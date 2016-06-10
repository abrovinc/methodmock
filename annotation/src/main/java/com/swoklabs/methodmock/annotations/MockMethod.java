package com.swoklabs.methodmock.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Steve on 2016-01-27.
 *
 * MockMethod will be triggered during the test phase.
 *
 * The id will be used to identify the specific MockMethod.
 * If the id is not unique then there is a chance for bad behaviour.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MockMethod {
    String id();
}
