package com.swoklabs.methodmock.test.project.classes;

import java.net.ConnectException;

/**
 * Created by Steve on 2016-01-27.
 */
public class PersonController {

    private PersonView personView;
    private DatabaseSimulator databaseSimulator;
    public PersonController(PersonView personView){
        this.personView = personView;
        this.databaseSimulator = new DatabaseSimulator();
    }

    public void getAndPrintPerson(String id) throws ConnectException {
        final Person person = databaseSimulator.getPersonFromDatabase(id);
        personView.printPerson(person);
    }
}
