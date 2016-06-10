package com.example;

import com.swoklabs.methodmock.annotations.MockMethod;

/**
 * Created by Steve Widinghoff on 2016-02-20.
 */
public class Example {
    @MockMethod(id = "methodToBeMocked")
    public static String methodToBeMocked() throws Exception {
        throw new Exception("Should not be thrown");
    }
}
