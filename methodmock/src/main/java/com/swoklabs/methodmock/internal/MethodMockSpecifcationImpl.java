package com.swoklabs.methodmock.internal;

import com.swoklabs.methodmock.model.Use;
import com.swoklabs.methodmock.model.exception.MethodReturnsVoidException;
import com.swoklabs.methodmock.model.exception.MockObjectClassDifferException;
import com.swoklabs.methodmock.specification.MethodMockSpecifcation;

public class MethodMockSpecifcationImpl implements MethodMockSpecifcation {

    private Use use = Use.ONCE;
    private Object returnObject = null;
    private String methodId = null;

    public MethodMockSpecifcationImpl(final String methodId){
        this.methodId = methodId;
    }

    public MethodMockSpecifcation calls(final Use use) throws MockObjectClassDifferException, MethodReturnsVoidException {
        this.use = use;
        return this;
    }

    public MethodMockSpecifcation returns(final Object returnObject) throws MockObjectClassDifferException, MethodReturnsVoidException {
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
