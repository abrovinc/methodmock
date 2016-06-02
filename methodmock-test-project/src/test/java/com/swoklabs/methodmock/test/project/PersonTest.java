package com.swoklabs.methodmock.test.project;

import com.swoklabs.methodmock.LoadJavaAgent;
import com.swoklabs.methodmock.model.exception.MethodReturnsVoidException;
import com.swoklabs.methodmock.model.exception.MockObjectClassDifferException;
import com.swoklabs.methodmock.test.project.classes.Person;
import com.swoklabs.methodmock.test.project.classes.PersonController;
import com.swoklabs.methodmock.test.project.classes.PersonView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.ConnectException;

import static com.swoklabs.methodmock.MethodMock.mockMethod;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
        Exception ex = null;
        final Person mockPerson = new Person("Steve","Widinghoff","abc");
        mockMethod("123").returns(mockPerson);
        try {
            personController.getAndPrintPerson("abc");
        } catch (ConnectException e) {
            ex = e;
            fail("Should not throw exception");
        }
        assertEquals(ex == null, true);
    }


    @Test
    public void testDbException(){
        Exception ex = null;
        try {
            personController.getAndPrintPerson("abc");
        } catch (ConnectException e) {
            ex = e;
            assertEquals("Could not connect and get",e.getMessage());
        }
        assertEquals(ex != null, true);
    }

}
