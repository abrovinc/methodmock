package com.swoklabs.amock.aspect;

import com.swoklabs.amock.LoadJavaAgent;
import com.swoklabs.amock.annotations.MockInTest;
import com.swoklabs.amock.model.Use;
import com.swoklabs.amock.model.exception.MethodReturnsVoidException;
import com.swoklabs.amock.model.exception.MockObjectClassDifferException;
import org.junit.Test;

import static com.swoklabs.amock.SwoklabAMock.clearMock;
import static com.swoklabs.amock.SwoklabAMock.mockMethod;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Steve Widinghoff on 2016-01-31.
 */
public class MockInterceptorTest extends LoadJavaAgent {

    @Test
    public void testMockInterceptor() throws MockObjectClassDifferException, MethodReturnsVoidException {
        mockMethod("isPublic").calls(Use.ONCE).returns(true);
        assertEquals(true, isPublicFalse());
        assertEquals(false, isPublicFalse());

        mockMethod("isPrivate").returns(true);
        assertEquals(true, isPrivateFalse());
        assertEquals(false, isPrivateFalse());

        mockMethod("isProtected").returns(true);
        assertEquals(true, isProtectedFalse());
        assertEquals(false, isProtectedFalse());

        mockMethod("isDefault").calls(Use.InfinitelyAndAddLast).returns(true);
        assertEquals(true, isDefaultFalse());
        assertEquals(true, isDefaultFalse());
        clearMock();
        assertEquals(false, isDefaultFalse());
        mockMethod("isDefault").returns(true);
        assertEquals(true, isDefaultFalse());
    }

    @MockInTest(methodId = "isPublic")
    public boolean isPublicFalse(){
        return false;
    }

    @MockInTest(methodId = "isPrivate")
    private boolean isPrivateFalse(){
        return false;
    }

    @MockInTest(methodId = "isProtected")
    protected boolean isProtectedFalse(){
        return false;
    }

    @MockInTest(methodId = "isDefault")
    boolean isDefaultFalse(){
        return false;
    }
}
