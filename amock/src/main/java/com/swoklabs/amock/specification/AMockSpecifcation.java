package com.swoklabs.amock.specification;

import com.swoklabs.amock.model.Use;
import com.swoklabs.amock.model.exception.MethodReturnsVoidException;
import com.swoklabs.amock.model.exception.MockObjectClassDifferException;

/**
 * Created by Steve Widinghoff on 2016-02-07.
 */
public interface AMockSpecifcation {

    /**
     * Specify the number of calls that the returns object should be saved
     * @param use
     * @return
     * @throws MockObjectClassDifferException
     * @throws MethodReturnsVoidException
     */
    AMockSpecifcation calls(Use use) throws MockObjectClassDifferException, MethodReturnsVoidException;

    /**
     * Specify the object that should be returned
     * @param returnObject
     * @return
     * @throws MockObjectClassDifferException
     * @throws MethodReturnsVoidException
     */
    AMockSpecifcation returns(Object returnObject) throws MockObjectClassDifferException, MethodReturnsVoidException;

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
