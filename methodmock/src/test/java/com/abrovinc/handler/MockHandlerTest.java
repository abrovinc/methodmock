package com.abrovinc.handler;

import com.abrovinc.MethodMock;
import com.abrovinc.model.exception.MethodReturnsVoidException;
import com.abrovinc.model.exception.MockObjectClassDifferException;
import com.abrovinc.LoadJavaAgent;
import com.abrovinc.methodmock.annotations.MockMethod;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Steve Widinghoff on 2016-01-31.
 */
public class MockHandlerTest extends LoadJavaAgent{

    @Test
    public void primitiveTypesTest() throws MockObjectClassDifferException, MethodReturnsVoidException {
        TestInnerClass testInnerClass = new TestInnerClass();


        //int
        int test = 1;
        MethodMock.mockMethod("testInt").returns(test);
        assertEquals(1, testInnerClass.testInt());

        //long
        MethodMock.mockMethod("testLong").returns(1L);
        assertEquals(1L, testInnerClass.testLong());

        //double
        MethodMock.mockMethod("testDouble").returns(1D);
        assertEquals(1D, testInnerClass.testDouble());

        //float
        MethodMock.mockMethod("testFloat").returns(1F);
        assertEquals(1F, testInnerClass.testFloat());

        //boolean
        MethodMock.mockMethod("testBoolean").returns(true);
        assertEquals(true, testInnerClass.testBoolean());

        //byte
        MethodMock.mockMethod("testByte").returns((byte) 0xe0);
        assertEquals((byte) 0xe0, testInnerClass.testByte());

        //char
        MethodMock.mockMethod("testChar").returns('b');
        assertEquals('b', testInnerClass.testChar());

        //short
        MethodMock.mockMethod("testShort").returns((short) 1);
        assertEquals((short) 1, testInnerClass.testShort());
    }

    private class TestInnerClass {
        @MockMethod(id = "testInt")
        private int testInt() {
            return -1;
        }

        @MockMethod(id = "testLong")
        private long testLong() {
            return -1L;
        }

        @MockMethod(id = "testDouble")
        private double testDouble() {
            return -1D;
        }

        @MockMethod(id = "testFloat")
        private float testFloat() {
            return -1F;
        }

        @MockMethod(id = "testBoolean")
        private boolean testBoolean() {
            return false;
        }

        @MockMethod(id = "testByte")
        private byte testByte() {
            return (byte) 0x1;
        }

        @MockMethod(id = "testChar")
        private char testChar() {
            return 'c';
        }

        @MockMethod(id = "testShort")
        private short testShort() {
            return -1;
        }
    }
}
