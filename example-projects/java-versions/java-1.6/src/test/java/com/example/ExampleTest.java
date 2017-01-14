package com.example;

import com.abrovinc.LoadJavaAgent;
import org.junit.Test;

import static com.abrovinc.MethodMock.mockMethod;
import static org.junit.Assert.assertEquals;

public class ExampleTest {

    @Test
    public void testExample() throws Exception {

        new Example();

        new LoadJavaAgent();

        mockMethod("methodToBeMocked").returns("Mock works");
        assertEquals("Mock works",Example.methodToBeMocked());
    }
}
