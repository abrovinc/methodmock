package com.swoklabs.amock.handler;

import com.swoklabs.amock.LoadJavaAgent;
import com.swoklabs.amock.annotations.MockInTest;
import com.swoklabs.amock.model.exception.MethodReturnsVoidException;
import com.swoklabs.amock.model.exception.MockObjectClassDifferException;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.swoklabs.amock.SwoklabAMock.mockMethod;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Steve Widinghoff on 2016-01-31.
 */
public class MockHandlerTest extends LoadJavaAgent{

    @BeforeClass
    public static void init(){
        new LoadJavaAgent();
    }

    @Test
    public void primitiveTypesTest() throws MockObjectClassDifferException, MethodReturnsVoidException {
        TestInnerClass testInnerClass = new TestInnerClass();


        //int
        int test = 1;
        mockMethod("testInt").returns(test);
        assertEquals(1, testInnerClass.testInt());

        //long
        mockMethod("testLong").returns(1L);
        assertEquals(1L, testInnerClass.testLong());

        //double
        mockMethod("testDouble").returns(1D);
        assertEquals(1D, testInnerClass.testDouble());

        //float
        mockMethod("testFloat").returns(1F);
        assertEquals(1F, testInnerClass.testFloat());

        //boolean
        mockMethod("testBoolean").returns(true);
        assertEquals(true, testInnerClass.testBoolean());

        //byte
        mockMethod("testByte").returns((byte) 0xe0);
        assertEquals((byte) 0xe0, testInnerClass.testByte());

        //char
        mockMethod("testChar").returns('b');
        assertEquals('b', testInnerClass.testChar());

        //short
        mockMethod("testShort").returns((short) 1);
        assertEquals((short) 1, testInnerClass.testShort());
    }

    private class TestInnerClass {
        @MockInTest(methodId = "testInt")
        private int testInt() {
            return -1;
        }

        @MockInTest(methodId = "testLong")
        private long testLong() {
            return -1L;
        }

        @MockInTest(methodId = "testDouble")
        private double testDouble() {
            return -1D;
        }

        @MockInTest(methodId = "testFloat")
        private float testFloat() {
            return -1F;
        }

        @MockInTest(methodId = "testBoolean")
        private boolean testBoolean() {
            return false;
        }

        @MockInTest(methodId = "testByte")
        private byte testByte() {
            return (byte) 0x1;
        }

        @MockInTest(methodId = "testChar")
        private char testChar() {
            return 'c';
        }

        @MockInTest(methodId = "testShort")
        private short testShort() {
            return -1;
        }
    }
}
