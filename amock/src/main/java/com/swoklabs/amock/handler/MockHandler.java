package com.swoklabs.amock.handler;

import com.swoklabs.amock.model.MockContainer;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Steve on 2016-01-27.
 */
public class MockHandler {

    private static InheritableThreadLocal<HashMap<String, Deque<MockContainer>>> localCache = new InheritableThreadLocal<HashMap<String, Deque<MockContainer>>>() {
        @Override
        protected HashMap<String, Deque<MockContainer>> initialValue() {
            return new HashMap<>();
        }
    };

    public static void registerMockContainer(final MockContainer mockContainer){
        final HashMap<String, Deque<MockContainer>> temp = localCache.get();
        Deque deque = temp.get(mockContainer.getMethodId());
        if (deque == null){
            deque = new LinkedList<MockContainer>();
            deque.add(mockContainer);
            temp.put(mockContainer.getMethodId(),deque);
        }
        else{
            deque.add(mockContainer);
        }
    }

    public Object handleMockCall(final ProceedingJoinPoint proceedingJoinPoint, final String methodId) throws Throwable {
        Object returnObj = null;
        final Deque<MockContainer> deque = localCache.get().get(methodId);
        if (deque != null){
            MockContainer mockContainer = deque.pollFirst();
            if(mockContainer != null){
                returnObj = mockContainer.getMockResponse();
            }
        }
        return returnObj;
    }
}
