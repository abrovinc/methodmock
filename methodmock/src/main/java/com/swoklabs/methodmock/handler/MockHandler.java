package com.swoklabs.methodmock.handler;

import com.swoklabs.methodmock.model.Use;
import com.swoklabs.methodmock.model.exception.MethodReturnsVoidException;
import com.swoklabs.methodmock.model.exception.MockObjectClassDifferException;
import com.swoklabs.methodmock.specification.MethodMockSpecifcation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.*;

public class MockHandler {

    private static InheritableThreadLocal<HashMap<String, Deque<MethodMockSpecifcation>>> localCache = new InheritableThreadLocal<HashMap<String, Deque<MethodMockSpecifcation>>>() {
        @Override
        protected HashMap<String, Deque<MethodMockSpecifcation>> initialValue() {
            return new HashMap<String, Deque<MethodMockSpecifcation>>();
        }
    };

    public static void registerMethodMockSpecification(final String methodId, final MethodMockSpecifcation methodMockSpecifcation) {

        final HashMap<String, Deque<MethodMockSpecifcation>> temp = localCache.get();
        Deque<MethodMockSpecifcation> deque = temp.get(methodId);

        if (deque == null) {
            deque = new LinkedList<MethodMockSpecifcation>();
            deque.add(methodMockSpecifcation);
            temp.put(methodId, deque);
        } else {
            deque.add(methodMockSpecifcation);
        }
    }

    public Object handleMockCall(final ProceedingJoinPoint proceedingJoinPoint, final String methodId) throws Throwable {
        final Object returnObj;

        //TODO Maybe add support to change inparameter values and not skip methods because they are void
        if (!isReturnTypeVoid(proceedingJoinPoint)) {
            final Deque<MethodMockSpecifcation> deque = localCache.get().get(methodId);
            returnObj = getReturnableObject(proceedingJoinPoint, deque);
        } else {
            throw new MethodReturnsVoidException("The framework does not support methods that return void");
        }
        return returnObj;
    }

    private Object getReturnableObject(final ProceedingJoinPoint proceedingJoinPoint, final Deque<MethodMockSpecifcation> deque) throws Throwable {
        final Object returnObj;
        if (deque != null) {

            final MethodMockSpecifcation methodMockSpecifcation = deque.pollFirst();
            if (methodMockSpecifcation != null) {
                final Object mockResponse = methodMockSpecifcation.getReturnObject();

                returnObj = getMockedObject(proceedingJoinPoint, deque, methodMockSpecifcation, mockResponse);
            } else {
                returnObj = proceedingJoinPoint.proceed();
            }
        } else {
            returnObj = proceedingJoinPoint.proceed();
        }
        return returnObj;
    }

    private Object getMockedObject(final ProceedingJoinPoint proceedingJoinPoint, final Deque<MethodMockSpecifcation> deque, final MethodMockSpecifcation methodMockSpecifcation, final Object mockResponse) throws Throwable {
        final Object returnObj;
        if (isMockObjectAndReturnTheSameType(mockResponse, proceedingJoinPoint)) {
            returnObj = mockResponse;

            //adds the mockable back to the Deque if the Use variable is correctly set
            if (methodMockSpecifcation.getUse().equals(Use.FOREVER)) {
                deque.add(methodMockSpecifcation);
            }
        } else if (mockResponse instanceof Exception) {
            throw (Throwable) mockResponse;
        } else {
            final Class returnType = getClassFromJointPoint(proceedingJoinPoint);
            throw new MockObjectClassDifferException("Classes differ, method expected to return a : " + returnType.getClass() + " but got : " + mockResponse.getClass());
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
        final boolean stevesDebugBoolean = (returnType.equals(mockResponse.getClass())
                || isPrimitive(returnType, mockResponse.getClass()));
        return stevesDebugBoolean;
    }

    private static boolean isPrimitive(Class returnType, Class mockResponse) {

        final Map<String, String> primitiveMapping = new HashMap<String, String>();
        primitiveMapping.put("int", "java.lang.Integer");
        primitiveMapping.put("long", "java.lang.Long");
        primitiveMapping.put("double", "java.lang.Double");
        primitiveMapping.put("float", "java.lang.Float");
        primitiveMapping.put("boolean", "java.lang.Boolean");
        primitiveMapping.put("byte", "java.lang.Byte");
        primitiveMapping.put("char", "java.lang.Character");
        primitiveMapping.put("short", "java.lang.Short");

        final String mappedValue = primitiveMapping.get(returnType.getName());
        final boolean isPrimitive = mockResponse.getName().equalsIgnoreCase(mappedValue);
        return isPrimitive;
    }

    private static Class getClassFromJointPoint(final JoinPoint joinPoint) {
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final Class clazz = methodSignature.getReturnType();
        return clazz;
    }

    public void clearMock() {
        localCache.remove();
    }
}
