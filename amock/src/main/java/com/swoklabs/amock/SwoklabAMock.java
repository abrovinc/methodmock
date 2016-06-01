package com.swoklabs.amock;

import com.swoklabs.amock.handler.MockHandler;
import com.swoklabs.amock.internal.AMockSpecifcationImpl;
import com.swoklabs.amock.specification.AMockSpecifcation;

public class SwoklabAMock {
    public static MockHandler mockHandler = new MockHandler();
    public static AMockSpecifcation mockMethod(final String methodId){
        final AMockSpecifcationImpl aMockSpecifcation =  new AMockSpecifcationImpl(methodId);
        mockHandler.registerAMockSpecification(methodId, aMockSpecifcation);
        return aMockSpecifcation;
    }

    public static void clearMock(){
        mockHandler.clearMock();
    }
}
