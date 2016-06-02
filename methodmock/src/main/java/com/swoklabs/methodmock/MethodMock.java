package com.swoklabs.methodmock;

import com.swoklabs.methodmock.handler.MockHandler;
import com.swoklabs.methodmock.internal.MethodMockSpecifcationImpl;
import com.swoklabs.methodmock.specification.MethodMockSpecifcation;

public class MethodMock {
    public static MockHandler mockHandler = new MockHandler();
    public static MethodMockSpecifcation mockMethod(final String methodId){
        final MethodMockSpecifcationImpl methodMockSpecifcation =  new MethodMockSpecifcationImpl(methodId);
        mockHandler.registerMethodMockSpecification(methodId, methodMockSpecifcation);
        return methodMockSpecifcation;
    }

    public static void clearMock(){
        mockHandler.clearMock();
    }
}
