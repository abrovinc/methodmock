package com.swoklabs.methodmock.test.project.classes;

/**
 * Created by Steve on 2016-01-27.
 */
public class Person {
    private final String surname;
    private final String firstname;
    private final String id;


    public Person(final String surname, final String firstname, final String id){
        this.surname = surname;
        this.firstname = firstname;
        this.id = id;
    }
    public String getSurname() {
        return surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getId() {
        return id;
    }
    @Override
    public String toString(){
        return "ID: "+id+" - FirstName : "+firstname+" - Surname : "+surname;
    }
}
