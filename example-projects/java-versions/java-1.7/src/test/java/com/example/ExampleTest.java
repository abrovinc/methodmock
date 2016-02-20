package com.example;

import com.swoklabs.amock.LoadJavaAgent;
import org.junit.Test;

import static com.swoklabs.amock.SwoklabAMock.mockMethod;
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
