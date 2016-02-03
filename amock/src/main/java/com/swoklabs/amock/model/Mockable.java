package com.swoklabs.amock.model;

/**
 * Created by Steve on 2016-01-27.
 */
public class Mockable {

    private final Object mockResponse;
    private final Use use;

    public Mockable(final Object mockResponse){
        this(Use.InfinitelyAndAddLast, mockResponse);
    }

    public Mockable(final Use use, final Object mockResponse) {
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

    public Use getUse() {
        return use;
    }
}
