package com.swoklabs.amock.model;

/**
 * Created by Steve on 2016-01-27.
 */
public class MockContainer {
    private final String methodId;
    private final Object mockResponse;

    public MockContainer(final String methodId, final Object mockResponse) {
        this.methodId = methodId;
        this.mockResponse = mockResponse;
    }

    public String getMethodId() {
        return methodId;
    }

    public Object getMockResponse() {
        return mockResponse;
    }

    @Override
    public String toString() {
        return methodId + " " + mockResponse.toString();
    }
}
