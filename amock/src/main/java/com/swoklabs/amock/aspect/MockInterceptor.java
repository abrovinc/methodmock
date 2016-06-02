package com.swoklabs.amock.aspect;

import com.swoklabs.amock.annotations.MockInTest;
import com.swoklabs.amock.handler.MockHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by Steve on 2016-01-27.
 */
@Aspect
public class MockInterceptor {
    private final MockHandler mockHandler = new MockHandler();
    @Pointcut("@annotation(mockInTest)")
    public void annotationMockInTest(MockInTest mockInTest) {
    }

    @Around("annotationMockInTest(mockInTest)")
    public Object aroundMockInTest(ProceedingJoinPoint proceedingJoinPoint, MockInTest mockInTest) throws Throwable {
        return mockHandler.handleMockCall(proceedingJoinPoint, mockInTest.methodId());
    }
}
