package com.swoklabs.amock.aspect;

import com.swoklabs.amock.LoadJavaAgent;
import com.swoklabs.amock.annotations.MockInTest;
import com.swoklabs.amock.model.Use;
import com.swoklabs.amock.model.exception.MethodReturnsVoidException;
import com.swoklabs.amock.model.exception.MockObjectClassDifferException;
import org.junit.Test;

import static com.swoklabs.amock.SwoklabAMock.mockMethod;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Steve Widinghoff on 2016-01-31.
 */
public class MockInterceptorTest extends LoadJavaAgent {

    @Test
    public void testMockInterceptor() throws MockObjectClassDifferException, MethodReturnsVoidException {
        mockMethod("isPublic").calls(Use.ONCE).returns(true);
        mockMethod("isPrivate").calls(Use.ONCE).returns(true);
        mockMethod("isProtected").calls(Use.ONCE).returns(true);
        mockMethod("isDefault").calls(Use.ONCE).returns(true);
        assertEquals(true, isPublicFalse());
        assertEquals(true, isPrivateFalse());
        assertEquals(true, isProtectedFalse());
        assertEquals(true, isDefaultFalse());

        assertEquals(false, isPublicFalse());
        assertEquals(false, isPrivateFalse());
        assertEquals(false, isProtectedFalse());
        assertEquals(false, isDefaultFalse());
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
