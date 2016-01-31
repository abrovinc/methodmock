package com.swoklabs.amock.test.project;

import com.swoklabs.amock.LoadJavaAgent;
import com.swoklabs.amock.handler.MockHandler;
import com.swoklabs.amock.model.MockContainer;
import com.swoklabs.amock.test.project.classes.Person;
import com.swoklabs.amock.test.project.classes.PersonController;
import com.swoklabs.amock.test.project.classes.PersonView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.ConnectException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Steve on 2016-01-27.
 */
public class PersonTest extends LoadJavaAgent {
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

        final Person mockPerson = new Person("Steve","Widisnghoff","abc");
        final MockContainer mockContainer = new MockContainer("123", mockPerson);
        MockHandler.registerMockContainer(mockContainer);
        try {
            personController.getAndPrintPerson("abc");
        } catch (ConnectException e) {
            fail("Should not throw exception");
        }
    }
}
