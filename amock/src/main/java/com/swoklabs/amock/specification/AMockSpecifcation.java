package com.swoklabs.amock.specification;

import com.swoklabs.amock.model.Use;
import com.swoklabs.amock.model.exception.MethodReturnsVoid;
import com.swoklabs.amock.model.exception.MockObjectClassDiffer;

/**
 * Created by Steve Widinghoff on 2016-02-07.
 */
public interface AMockSpecifcation {

    /**
     * Specify the number of calls that the returns object should be saved
     * @param use
     * @return
     * @throws MockObjectClassDiffer
     * @throws MethodReturnsVoid
     */
    AMockSpecifcation calls(Use use) throws MockObjectClassDiffer, MethodReturnsVoid;

    /**
     * Specify the object that should be returned
     * @param returnObject
     * @return
     * @throws MockObjectClassDiffer
     * @throws MethodReturnsVoid
     */
    AMockSpecifcation returns(Object returnObject) throws MockObjectClassDiffer, MethodReturnsVoid;

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
