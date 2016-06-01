package com.swoklabs.amock.handler;

import com.swoklabs.amock.model.Use;
import com.swoklabs.amock.model.exception.MethodReturnsVoidException;
import com.swoklabs.amock.model.exception.MockObjectClassDifferException;
import com.swoklabs.amock.specification.AMockSpecifcation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.*;

public class MockHandler {

    private static InheritableThreadLocal<HashMap<String, Deque<AMockSpecifcation>>> localCache = new InheritableThreadLocal<HashMap<String, Deque<AMockSpecifcation>>>() {
        @Override
        protected HashMap<String, Deque<AMockSpecifcation>> initialValue() {
            return new HashMap<String, Deque<AMockSpecifcation>>();
        }
    };

    public static void registerAMockSpecification(final String methodId, final AMockSpecifcation aMockSpecifcation) {

        final HashMap<String, Deque<AMockSpecifcation>> temp = localCache.get();
        Deque<AMockSpecifcation> deque = temp.get(methodId);

        if (deque == null) {
            deque = new LinkedList<AMockSpecifcation>();
            deque.add(aMockSpecifcation);
            temp.put(methodId, deque);
        } else {
            deque.add(aMockSpecifcation);
        }
    }

    public Object handleMockCall(final ProceedingJoinPoint proceedingJoinPoint, final String methodId) throws Throwable {
        final Object returnObj;

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
                    } else if (mockResponse instanceof Exception) {
                        throw (Throwable) mockResponse;
                    } else {
                        final Class returnType = getClassFromJointPoint(proceedingJoinPoint);
                        throw new MockObjectClassDifferException("Classes differ, method expected to return a : " + returnType.getClass() + " but got : " + mockResponse.getClass());
                    }
                } else {
                    returnObj = proceedingJoinPoint.proceed();
                }
            } else {
                returnObj = proceedingJoinPoint.proceed();
            }
        } else {
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

        final Class returnType = getClassFromJointPoint(proceedingJoinPoint);
        final boolean isMockObjectAndReturnTheSameType = (returnType.equals(mockResponse.getClass()) || isPrimitive(returnType, mockResponse.getClass()));
        return isMockObjectAndReturnTheSameType;
    }

    private static Boolean isPrimitive(Class returnType, Class mockResponse) {

        final Map<String, String> primitiveMapping = new HashMap<String, String>();
        primitiveMapping.put("int", "java.lang.Integer");
        primitiveMapping.put("long", "java.lang.Long");
        primitiveMapping.put("double", "java.lang.Double");
        primitiveMapping.put("float", "java.lang.Float");
        primitiveMapping.put("boolean", "java.lang.Boolean");
        primitiveMapping.put("byte", "java.lang.Byte");
        primitiveMapping.put("char", "java.lang.Character");
        primitiveMapping.put("short", "java.lang.Short");

        final Boolean isPrimitive;
        final String mappedValue = primitiveMapping.get(returnType.getName());
        if (mappedValue != null && mappedValue.equalsIgnoreCase(mockResponse.getName())) {
            isPrimitive = new Boolean(true);
        } else {
            isPrimitive = new Boolean(false);
        }
        return isPrimitive;
    }

    private static Class getClassFromJointPoint(final JoinPoint joinPoint) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final Class aClass = methodSignature.getReturnType();
        return aClass;
    }
}
