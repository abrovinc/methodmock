package com.swoklabs.amock.handler;

import com.swoklabs.amock.model.Mockable;
import com.swoklabs.amock.model.Use1;
import com.swoklabs.amock.model.exception.MethodReturnsVoid;
import com.swoklabs.amock.model.exception.MockObjectClassDiffer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Steve on 2016-01-27.
 */
public class MockHandler {

    private static InheritableThreadLocal<HashMap<String, Deque<Mockable>>> localCache = new InheritableThreadLocal<HashMap<String, Deque<Mockable>>>() {
        @Override
        protected HashMap<String, Deque<Mockable>> initialValue() {
            return new HashMap<>();
        }
    };

    public static void registerMockContainer(final String methodId, final Mockable mockable) {

        final HashMap<String, Deque<Mockable>> temp = localCache.get();
        Deque deque = temp.get(methodId);

        if (deque == null) {
            deque = new LinkedList<Mockable>();
            deque.add(mockable);
            temp.put(methodId, deque);
        } else {
            deque.add(mockable);
        }
    }

    public Object handleMockCall(final ProceedingJoinPoint proceedingJoinPoint, final String methodId) throws Throwable {
        Object returnObj = null;

        //TODO Maybe add support to change inparameter values and not skip methods because they are void
        if (!isReturnTypeVoid(proceedingJoinPoint)) {
            final Deque<Mockable> deque = localCache.get().get(methodId);

            if (deque != null) {

                final Mockable mockable = deque.pollFirst();
                if (mockable != null) {

                    final Object mockResponse = mockable.getMockResponse();

                    if (isMockObjectAndReturnTheSameType(mockResponse, proceedingJoinPoint)) {
                        returnObj = mockResponse;

                        //adds the mockable back to the Deque if the Use variable is correctly set
                        if (mockable.getUse().equals(Use.InfinitelyAndAddLast)) {
                            deque.add(mockable);
                        }
                    } else {
                        throw new MockObjectClassDiffer("Classes differ, method expected to return a : X but got : "+mockResponse.getClass());
                    }
                }
            }
        }
        else {
            throw new MethodReturnsVoid("The framework does not support methods that return void");
        }

        return returnObj;
    }

    private static boolean isReturnTypeVoid(final ProceedingJoinPoint proceedingJoinPoint) {
        final MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        final boolean isReturnTypeVoid = methodSignature.getReturnType().equals(Void.TYPE);
        return isReturnTypeVoid;
    }

    private static boolean isMockObjectAndReturnTheSameType(final Object mockResponse, final ProceedingJoinPoint proceedingJoinPoint) {

        final MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        final Class returnType = methodSignature.getReturnType();
        final boolean isMockObjectAndReturnTheSameType = returnType.equals(mockResponse.getClass());
        return isMockObjectAndReturnTheSameType;
    }
}
