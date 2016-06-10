package com.swoklabs.methodmock.test.project.classes;

import com.swoklabs.methodmock.annotations.MockInTest;

import java.net.ConnectException;

/**
 * Created by Steve on 2016-01-27.
 */
public class DatabaseSimulator {

    @MockInTest(methodId = "123")
    public Person getPersonFromDatabase(String personId) throws ConnectException {
        throw new ConnectException("Could not connect and get");
    }

    @MockInTest(methodId = "1233")
    public void savePersonFromDatabase(String personId) throws ConnectException {
        throw new ConnectException("Could not connect and not save");
    }
}
