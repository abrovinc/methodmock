package com.swoklabs.methodmock.aspect;

import com.swoklabs.methodmock.annotations.MockMethod;
import com.swoklabs.methodmock.handler.MockHandler;
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
    @Pointcut("@annotation(mockMethod)")
    public void annotationMockInTest(MockMethod mockMethod) {
    }

    @Around("annotationMockInTest(mockMethod)")
    public Object aroundMockInTest(ProceedingJoinPoint proceedingJoinPoint, MockMethod mockMethod) throws Throwable {
        return mockHandler.handleMockCall(proceedingJoinPoint, mockMethod.id());
    }
}
