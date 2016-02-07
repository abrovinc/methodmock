package com.swoklabs.amock;

import com.swoklabs.amock.handler.MockHandler;
import com.swoklabs.amock.internal.AMockSpecifcationImpl;
import com.swoklabs.amock.specification.AMockSpecifcation;

/**
 * Created by Steve Widinghoff on 2016-02-07.
 */
public class SwoklabAMock {
    public static MockHandler mockHandler = new MockHandler();
    public static AMockSpecifcation mockMethod(String methodId){
        final AMockSpecifcationImpl aMockSpecifcation =  new AMockSpecifcationImpl(methodId);
        mockHandler.registerAMockSpecification(methodId, aMockSpecifcation);
        return aMockSpecifcation;
    }
}
