package com.swoklabs.amock.handler;

import com.swoklabs.amock.model.MockContainer;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by Steve on 2016-01-27.
 */
public class MockHandler {
    private ThreadLocal<Deque<MockContainer>> mockContainers = new ThreadLocal<Deque<MockContainer>>() {
        @Override
        protected Deque<MockContainer> initialValue() {
            return new LinkedList<>();
        }
    };

    public void registerMockContainer(final MockContainer mockContainer){
        System.out.println("Adding to mock");
        mockContainers.get().add(mockContainer);
    }

    public Object handleMockCall(final ProceedingJoinPoint proceedingJoinPoint, final String methodId) throws Throwable {
        System.out.println("Handling call");
        Object returnObj = null;
        if (mockContainers.get().size() > 0){
            return proceedingJoinPoint.proceed();
        }
        return returnObj;
    }
}
