package com.swoklabs.amock.model;

/**
 * Created by Steve on 2016-01-27.
 */
public class Mockable {

    private final Object mockResponse;
    private final Use1 use;

    public Mockable(final Object mockResponse){
        this(Use1.InfinitelyAndAddLast, mockResponse);
    }

    public Mockable(final Use1 use, final Object mockResponse) {
        this.mockResponse = mockResponse;
        this.use = use;
    }


    public Object getMockResponse() {
        return mockResponse;
    }

    @Override
    public String toString() {
        return mockResponse.toString();
    }

    public Use1 getUse() {
        return use;
    }
}
