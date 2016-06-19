package com.abrovinc.test.project;

import com.abrovinc.MethodMock;
import com.abrovinc.model.exception.MethodReturnsVoidException;
import com.abrovinc.model.exception.MockObjectClassDifferException;
import com.abrovinc.test.project.classes.PersonController;
import com.abrovinc.test.project.classes.PersonView;
import com.abrovinc.LoadJavaAgent;
import com.abrovinc.test.project.classes.Person;
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

    @Test
    public void testDbException(){
        try {
            personController.getAndPrintPerson("abc");
        } catch (ConnectException e) {
            assertEquals("Could not connect and get",e.getMessage());
        }
    }

    @Test
    public void testMockedDbCall() throws MockObjectClassDifferException, MethodReturnsVoidException {

        final Person mockPerson = new Person("Steve","Widisnghoff","abc");
        MethodMock.mockMethod("123").returns(mockPerson);
        try {
            personController.getAndPrintPerson("abc");
        } catch (ConnectException e) {
            fail("Should not throw exception");
        }
    }
}
