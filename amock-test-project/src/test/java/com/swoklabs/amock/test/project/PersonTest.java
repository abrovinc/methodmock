package com.swoklabs.amock.test.project;

import com.swoklabs.amock.LoadJavaAgent;
import com.swoklabs.amock.model.exception.MethodReturnsVoidException;
import com.swoklabs.amock.model.exception.MockObjectClassDifferException;
import com.swoklabs.amock.test.project.classes.Person;
import com.swoklabs.amock.test.project.classes.PersonController;
import com.swoklabs.amock.test.project.classes.PersonView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.ConnectException;

import static com.swoklabs.amock.SwoklabAMock.mockMethod;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Steve on 2016-01-27.
 */
public class PersonTest extends LoadJavaAgent {
    final PersonView personView = new PersonView();
    final PersonController personController = new PersonController(personView);
    @Before
    public void setup(){
    }

    @After
    public void teardown(){

    }

    @Test
    public void testMockedDbCall() throws MockObjectClassDifferException, MethodReturnsVoidException {

        final Person mockPerson = new Person("Steve","Widinghoff","abc");
        mockMethod("123").returns(mockPerson);
        try {
            personController.getAndPrintPerson("abc");
        } catch (ConnectException e) {
            fail("Should not throw exception");
        }
    }

    @Test
    public void testDbException(){
        try {
            personController.getAndPrintPerson("abc");
        } catch (ConnectException e) {
            System.out.println("Exception is thrown : "+e.getMessage());
            assertEquals("Could not connect and get",e.getMessage());
        }
    }

}
