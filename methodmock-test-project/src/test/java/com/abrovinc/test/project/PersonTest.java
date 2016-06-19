package com.abrovinc.test.project;

import com.abrovinc.MethodMock;
import com.abrovinc.model.exception.MockObjectClassDifferException;
import com.abrovinc.test.project.classes.Person;
import com.abrovinc.test.project.classes.PersonController;
import com.abrovinc.LoadJavaAgent;
import com.abrovinc.model.exception.MethodReturnsVoidException;
import com.abrovinc.test.project.classes.PersonView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.ConnectException;

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
        MethodMock.mockMethod("123").returns(mockPerson);
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
