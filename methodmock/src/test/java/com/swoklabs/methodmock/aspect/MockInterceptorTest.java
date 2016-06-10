package com.swoklabs.methodmock.aspect;

import com.swoklabs.methodmock.LoadJavaAgent;
import com.swoklabs.methodmock.annotations.MockInTest;
import com.swoklabs.methodmock.model.Use;
import com.swoklabs.methodmock.model.exception.MethodReturnsVoidException;
import com.swoklabs.methodmock.model.exception.MockObjectClassDifferException;
import org.junit.Test;

import static com.swoklabs.methodmock.MethodMock.clearMock;
import static com.swoklabs.methodmock.MethodMock.mockMethod;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Steve Widinghoff on 2016-01-31.
 */
public class MockInterceptorTest extends LoadJavaAgent{

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

        mockMethod("isDefault").calls(Use.REUSE).returns(true);
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
