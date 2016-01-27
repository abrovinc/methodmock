package com.swoklabs.amock.test.project;

import com.swoklabs.amock.test.project.classes.PersonController;
import com.swoklabs.amock.test.project.classes.PersonView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.ConnectException;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Steve on 2016-01-27.
 */
public class PersonTest {
    private final PersonView personView = new PersonView();
    private final PersonController personController = new PersonController(personView);

    @Before
    public void setup(){

    }

    @After
    public void teardown(){

    }

    @Test
    public void testDbException(){
        try {
            personController.getAndPrintPerson("abc");
        } catch (ConnectException e) {
            assertEquals("Could not connect and get",e.getMessage());
        }
    }

    @Test
    public void testMockedDbCall(){
        
    }
}
