package com.swoklabs.amock.test.project;

import com.swoklabs.amock.LoadJavaAgent;
import com.swoklabs.amock.model.exception.MethodReturnsVoidException;
import com.swoklabs.amock.model.exception.MockObjectClassDifferException;
import com.swoklabs.amock.test.project.classes.PersonController;
import com.swoklabs.amock.test.project.classes.PersonView;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.ConnectException;

import static com.swoklabs.amock.SwoklabAMock.mockMethod;

/**
 * Created by Steve Widinghoff on 2016-02-20.
 */
public class MockExceptionTest extends LoadJavaAgent {

    final PersonView personView = new PersonView();
    final PersonController personController = new PersonController(personView);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throwNormalException() throws ConnectException {
        thrown.expect(ConnectException.class);
        thrown.expectMessage("Could not connect and get");
        personController.getAndPrintPerson("abc");
    }

    @Test
    public void throwMockException() throws MockObjectClassDifferException, MethodReturnsVoidException, ConnectException {
        thrown.expect(Exception.class);
        thrown.expectMessage("Mock exception");
        mockMethod("123").returns(new Exception("Mock exception"));
        personController.getAndPrintPerson("abc");
    }

    @Test
    public void throwMethodReturnsVoid() throws MockObjectClassDifferException, MethodReturnsVoidException, ConnectException {
        thrown.expect(MethodReturnsVoidException.class);
        thrown.expectMessage("The framework does not support methods that return void");
        mockMethod("1233").returns("should failand throw exception");
        personController.savePersonId("abc");
    }

    @Test
    public void throwMockObjectClassDiffer() throws MockObjectClassDifferException, MethodReturnsVoidException, ConnectException {
        thrown.expect(MockObjectClassDifferException.class);
        thrown.expectMessage("Classes differ, method expected to return a : X but got : class java.lang.String");
        mockMethod("123").returns("Should throw exception");
        personController.getAndPrintPerson("abc");
    }

}
