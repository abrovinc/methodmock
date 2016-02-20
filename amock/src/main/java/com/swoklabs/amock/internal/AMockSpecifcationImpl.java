package com.swoklabs.amock.internal;

import com.swoklabs.amock.model.Use;
import com.swoklabs.amock.model.exception.MethodReturnsVoid;
import com.swoklabs.amock.model.exception.MockObjectClassDiffer;
import com.swoklabs.amock.specification.AMockSpecifcation;

/**
 * Created by Steve Widinghoff on 2016-02-07.
 */
public class AMockSpecifcationImpl implements AMockSpecifcation {

    private Use use = Use.ONCE;
    private Object returnObject = null;
    private String methodId = null;

    public AMockSpecifcationImpl(String methodId){
        this.methodId = methodId;
    }

    public AMockSpecifcation calls(Use use) throws MockObjectClassDiffer, MethodReturnsVoid {
        this.use = use;
        return this;
    }

    public AMockSpecifcation returns(Object returnObject) throws MockObjectClassDiffer, MethodReturnsVoid {
        this.returnObject = returnObject;
        return this;
    }

    public Use getUse() {
        return use;
    }

    public Object getReturnObject() {
        return returnObject;
    }

    public String getMethodId() {
        return methodId;
    }
}
