package com.abrovinc;

import com.abrovinc.handler.MockHandler;
import com.abrovinc.internal.MethodMockSpecifcationImpl;
import com.abrovinc.specification.MethodMockSpecifcation;

public class MethodMock {

    public static MockHandler mockHandler = new MockHandler();

    public static MethodMockSpecifcation mockMethod(final String methodId){
        final MethodMockSpecifcationImpl methodMockSpecification =  new MethodMockSpecifcationImpl(methodId);// TODO: Spelling on variable is wrong.
        mockHandler.registerMethodMockSpecification(methodId, methodMockSpecification);
        return methodMockSpecification;
    }

    public static void clearMock(){
        mockHandler.clearMock();
    }
}
