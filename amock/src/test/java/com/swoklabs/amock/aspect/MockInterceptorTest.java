package com.swoklabs.amock.aspect;

import com.swoklabs.amock.LoadJavaAgent;
import com.swoklabs.amock.annotations.MockInTest;
import com.swoklabs.amock.model.Use;
import com.swoklabs.amock.model.exception.MethodReturnsVoidException;
import com.swoklabs.amock.model.exception.MockObjectClassDifferException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.swoklabs.amock.SwoklabAMock.clearMock;
import static com.swoklabs.amock.SwoklabAMock.mockMethod;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Steve Widinghoff on 2016-01-31.
 */
public class MockInterceptorTest extends LoadJavaAgent{

    @BeforeClass
    public static void init(){
        new LoadJavaAgent();
    }


    @Test
    public void testMockInterceptor() throws MockObjectClassDifferException, MethodReturnsVoidException {
        TestInnerClass testInnerClass = new TestInnerClass();
        mockMethod("isPublic").calls(Use.ONCE).returns(true);
        assertEquals(true, testInnerClass.isPublicFalse());
        assertEquals(false, testInnerClass.isPublicFalse());

        mockMethod("isPrivate").returns(true);
        assertEquals(true, testInnerClass.isPrivateFalse());
        assertEquals(false, testInnerClass.isPrivateFalse());

        mockMethod("isProtected").returns(true);
        assertEquals(true, testInnerClass.isProtectedFalse());
        assertEquals(false, testInnerClass.isProtectedFalse());

        mockMethod("isDefault").calls(Use.InfinitelyAndAddLast).returns(true);
        assertEquals(true, testInnerClass.isDefaultFalse());
        assertEquals(true, testInnerClass.isDefaultFalse());
        clearMock();
        assertEquals(false, testInnerClass.isDefaultFalse());
        mockMethod("isDefault").returns(true);
        assertEquals(true, testInnerClass.isDefaultFalse());
    }

    private class TestInnerClass {
        @MockInTest(methodId = "isPublic")
        public boolean isPublicFalse() {
            return false;
        }

        @MockInTest(methodId = "isPrivate")
        private boolean isPrivateFalse() {
            return false;
        }

        @MockInTest(methodId = "isProtected")
        protected boolean isProtectedFalse() {
            return false;
        }

        @MockInTest(methodId = "isDefault")
        boolean isDefaultFalse() {
            return false;
        }
    }
}
