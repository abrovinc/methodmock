package com.example;

import com.swoklabs.amock.annotations.MockInTest;

/**
 * Created by Steve Widinghoff on 2016-02-20.
 */
public class Example {
    @MockInTest(methodId = "methodToBeMocked")
    public static String methodToBeMocked() throws Exception {
        throw new Exception("Should not be thrown");
    }
}
