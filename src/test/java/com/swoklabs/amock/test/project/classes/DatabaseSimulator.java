package com.swoklabs.amock.test.project.classes;

import java.net.ConnectException;

/**
 * Created by Steve on 2016-01-27.
 */
public class DatabaseSimulator {

    public Person getPersonFromDatabase(String personId) throws ConnectException {
        throw new ConnectException("Could not connect and get");
    }

    public void savePersonFromDatabase(String personId) throws ConnectException {
        throw new ConnectException("Could not connect and not save");
    }
}
