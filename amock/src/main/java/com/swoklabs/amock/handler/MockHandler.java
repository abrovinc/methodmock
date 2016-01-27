package com.swoklabs.amock.handler;

import com.swoklabs.amock.model.MockContainer;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by Steve on 2016-01-27.
 */
public class MockHandler {
    private static InheritableThreadLocal<Deque<MockContainer>> mockContainers = new InheritableThreadLocal<Deque<MockContainer>>() {
        @Override
        protected Deque<MockContainer> initialValue() {
            return new LinkedList<>();
        }
    };

    public static void registerMockContainer(final MockContainer mockContainer){
        mockContainers.get().add(mockContainer);
    }

    public Object handleMockCall(final ProceedingJoinPoint proceedingJoinPoint, final String methodId) throws Throwable {
        Object returnObj = null;
        if (mockContainers.get().size() <= 0){
            return proceedingJoinPoint.proceed();
        }
        return mockContainers.get().pop().getMockResponse();
    }
}
