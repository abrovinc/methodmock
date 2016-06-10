package com.example;

import com.swoklabs.methodmock.LoadJavaAgent;
import org.junit.Test;

import static com.swoklabs.methodmock.MethodMock.mockMethod;
import static org.junit.Assert.assertEquals;

/**
 * Created by Steve Widinghoff on 2016-02-20.
 */
public class ExampleTest extends LoadJavaAgent {

    @Test
    public void testExample() throws Exception {
        mockMethod("methodToBeMocked").returns("Mock works");
        assertEquals("Mock works",Example.methodToBeMocked());
    }
}
