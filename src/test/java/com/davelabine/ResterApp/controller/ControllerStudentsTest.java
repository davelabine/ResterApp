package com.davelabine.resterapp.controller;

import com.davelabine.resterapp.model.Student;
import com.davelabine.resterapp.service.StudentManager;
import org.apache.http.HttpStatus;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;

/**
 * Created by davidl on 10/10/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ControllerStudentsTest {
    public static final String FAKE_KEY = "FAKE_KEY";
    public static final String FAKE_ID = "FAKE_ID";
    public static final String FAKE_NAME = "FAKE_NAME";

    @InjectMocks
    private ControllerStudents underTest;

    @Mock
    private StudentManager mockStudentManager;

    @Before
    public void before() throws Exception {

    }

    @Test
    public void postStudentTestFailed() throws URISyntaxException {
        reset(mockStudentManager);
        doReturn(null).when(mockStudentManager).createStudent(any(Student.class));

        Student createStudent = new Student(FAKE_ID, FAKE_NAME);
        Response response = underTest.create(false, createStudent);
        assertEquals(response.getStatus(), HttpStatus.SC_SERVICE_UNAVAILABLE);
    }

    @Test
    public void postStudentTest() throws URISyntaxException {
        reset(mockStudentManager);
        doReturn(FAKE_KEY).when(mockStudentManager).createStudent(any(Student.class));

        Student fakeStudent = new Student(FAKE_ID, FAKE_NAME);
        Response response = underTest.create(false, fakeStudent);
        assertEquals(response.getStatus(), HttpStatus.SC_CREATED);

        // TODO - check that returned URL has the FAKE_KEY in it
        //String responseText = response.readEntity(String.class);
        //assertTrue(responseText.contains(FAKE_KEY));
    }

    @Test
    public void getStudentMissingTest() throws URISyntaxException {
        reset(mockStudentManager);
        doReturn(null).when(mockStudentManager).getStudent(anyString());

        Response response = underTest.get(false, FAKE_KEY);
        assertEquals(response.getStatus(), HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void getStudentTest() throws URISyntaxException {
        reset(mockStudentManager);
        Student fakeStudent = new Student(FAKE_ID, FAKE_NAME);
        doReturn(fakeStudent).when(mockStudentManager).getStudent(anyString());

        Response response = underTest.get(false, FAKE_KEY);
        assertEquals(response.getStatus(), HttpStatus.SC_OK);

        // TODO - Check that the returned student has the ID we set
        //String responseText = response.readEntity(String.class);
        //assertTrue(responseText.contains(FAKE_ID));
    }

}
