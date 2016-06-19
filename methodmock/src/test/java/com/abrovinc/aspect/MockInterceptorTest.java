package com.abrovinc.aspect;

import com.abrovinc.MethodMock;
import com.abrovinc.model.exception.MockObjectClassDifferException;
import com.abrovinc.LoadJavaAgent;
import com.abrovinc.methodmock.annotations.MockMethod;
import com.abrovinc.model.Use;
import com.abrovinc.model.exception.MethodReturnsVoidException;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Steve Widinghoff on 2016-01-31.
 */
public class MockInterceptorTest extends LoadJavaAgent{

    @Test
    public void testMockInterceptor() throws MockObjectClassDifferException, MethodReturnsVoidException {
        TestInnerClass testInnerClass = new TestInnerClass();
        MethodMock.mockMethod("isPublic").calls(Use.ONCE).returns(true);
        assertEquals(true, testInnerClass.isPublicFalse());
        assertEquals(false, testInnerClass.isPublicFalse());

        MethodMock.mockMethod("isPrivate").returns(true);
        assertEquals(true, testInnerClass.isPrivateFalse());
        assertEquals(false, testInnerClass.isPrivateFalse());

        MethodMock.mockMethod("isProtected").returns(true);
        assertEquals(true, testInnerClass.isProtectedFalse());
        assertEquals(false, testInnerClass.isProtectedFalse());

        MethodMock.mockMethod("isDefault").calls(Use.REUSE).returns(true);
        assertEquals(true, testInnerClass.isDefaultFalse());
        assertEquals(true, testInnerClass.isDefaultFalse());
        MethodMock.clearMock();
        assertEquals(false, testInnerClass.isDefaultFalse());
        MethodMock.mockMethod("isDefault").returns(true);
        assertEquals(true, testInnerClass.isDefaultFalse());
    }

    private class TestInnerClass {
        @MockMethod(id = "isPublic")
        public boolean isPublicFalse() {
            return false;
        }

        @MockMethod(id = "isPrivate")
        private boolean isPrivateFalse() {
            return false;
        }

        @MockMethod(id = "isProtected")
        protected boolean isProtectedFalse() {
            return false;
        }

        @MockMethod(id = "isDefault")
        boolean isDefaultFalse() {
            return false;
        }
    }
}
