package com.swoklabs.methodmock.test.project.classes;

import com.swoklabs.methodmock.annotations.MockMethod;

import java.net.ConnectException;

/**
 * Created by Steve on 2016-01-27.
 */
public class DatabaseSimulator {

    @MockMethod(id = "123")
    public Person getPersonFromDatabase(String personId) throws ConnectException {
        throw new ConnectException("Could not connect and get");
    }

    @MockMethod(id = "1233")
    public void savePersonFromDatabase(String personId) throws ConnectException {
        throw new ConnectException("Could not connect and not save");
    }
}
