package com.swoklabs.amock.handler;

import com.swoklabs.amock.model.Use;
import com.swoklabs.amock.model.exception.MethodReturnsVoidException;
import com.swoklabs.amock.model.exception.MockObjectClassDifferException;
import com.swoklabs.amock.specification.AMockSpecifcation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Steve on 2016-01-27.
 */
public class MockHandler {

    private static InheritableThreadLocal<HashMap<String, Deque<AMockSpecifcation>>> localCache = new InheritableThreadLocal<HashMap<String, Deque<AMockSpecifcation>>>() {
        @Override
        protected HashMap<String, Deque<AMockSpecifcation>> initialValue() {
            return new HashMap<String, Deque<AMockSpecifcation>>();
        }
    };

    public static void registerAMockSpecification(final String methodId, final AMockSpecifcation aMockSpecifcation) {

        final HashMap<String, Deque<AMockSpecifcation>> temp = localCache.get();
        Deque deque = temp.get(methodId);

        if (deque == null) {
            deque = new LinkedList<AMockSpecifcation>();
            deque.add(aMockSpecifcation);
            temp.put(methodId, deque);
        } else {
            deque.add(aMockSpecifcation);
        }
    }

    public Object handleMockCall(final ProceedingJoinPoint proceedingJoinPoint, final String methodId) throws Throwable {
        Object returnObj = null;

        //TODO Maybe add support to change inparameter values and not skip methods because they are void
        if (!isReturnTypeVoid(proceedingJoinPoint)) {
            final Deque<AMockSpecifcation> deque = localCache.get().get(methodId);

            if (deque != null) {

                final AMockSpecifcation aMockSpecifcation = deque.pollFirst();
                if (aMockSpecifcation != null) {
                    final Object mockResponse = aMockSpecifcation.getReturnObject();

                    if (isMockObjectAndReturnTheSameType(mockResponse, proceedingJoinPoint)) {
                        returnObj = mockResponse;

                        //adds the mockable back to the Deque if the Use variable is correctly set
                        if (aMockSpecifcation.getUse().equals(Use.InfinitelyAndAddLast)) {
                            deque.add(aMockSpecifcation);
                        }
                    }
                    else if (mockResponse instanceof Exception || mockResponse instanceof RuntimeException){
                        throw (Throwable) mockResponse;
                    }
                    else {
                        throw new MockObjectClassDifferException("Classes differ, method expected to return a : X but got : "+mockResponse.getClass());
                    }
                }
                else {
                    returnObj = proceedingJoinPoint.proceed();
                }
            }
            else {
                returnObj = proceedingJoinPoint.proceed();
            }
        }
        else {
            throw new MethodReturnsVoidException("The framework does not support methods that return void");
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
