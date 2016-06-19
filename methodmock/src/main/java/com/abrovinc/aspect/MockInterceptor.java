package com.abrovinc.aspect;

import com.abrovinc.handler.MockHandler;
import com.abrovinc.methodmock.annotations.MockMethod;
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
