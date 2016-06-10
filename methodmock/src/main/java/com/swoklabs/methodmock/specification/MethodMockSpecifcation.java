package com.swoklabs.methodmock.specification;

import com.swoklabs.methodmock.model.Use;
import com.swoklabs.methodmock.model.exception.MethodReturnsVoidException;
import com.swoklabs.methodmock.model.exception.MockObjectClassDifferException;

/**
 * Created by Steve Widinghoff on 2016-02-07.
 */
public interface MethodMockSpecifcation {

    /**
     * Specify the number of calls that the returns object should be saved
     * @param use
     * @return
     * @throws MockObjectClassDifferException
     * @throws MethodReturnsVoidException
     */
    MethodMockSpecifcation calls(Use use) throws MockObjectClassDifferException, MethodReturnsVoidException;

    /**
     * Specify the object that should be returned
     * @param returnObject
     * @return
     * @throws MockObjectClassDifferException
     * @throws MethodReturnsVoidException
     */
    MethodMockSpecifcation returns(Object returnObject) throws MockObjectClassDifferException, MethodReturnsVoidException;

    /**
     *
     * @return
     */
    Use getUse();

    /**
     *
     * @return
     */
    Object getReturnObject();

    /**
     *
     * @return
     */
    String getMethodId();

}
