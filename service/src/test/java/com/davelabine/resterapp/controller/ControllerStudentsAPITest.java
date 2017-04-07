package com.davelabine.resterapp.controller;

import com.davelabine.resterapp.platform.api.model.Student;
import com.davelabine.resterapp.service.StudentManager;
import org.apache.http.HttpStatus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * Created by davidl on 10/10/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ControllerStudentsAPITest {
    public static final String FAKE_KEY = "FAKE_KEY";
    public static final String FAKE_ID = "FAKE_ID";
    public static final String FAKE_NAME = "FAKE_NAME";

    @InjectMocks
    private ControllerStudentsAPI underTest;

    @Mock
    private StudentManager mockStudentManager;

    @Before
    public void before() throws Exception {

    }

    @Test
    public void postStudentTest() throws URISyntaxException {
        reset(mockStudentManager);
        doReturn(FAKE_KEY).when(mockStudentManager).createStudent(any(Student.class), nullable(InputStream.class));

        Student fakeStudent = new Student(FAKE_ID, FAKE_NAME);
        Response response = underTest.create(0, fakeStudent);
        assertEquals(response.getStatus(), HttpStatus.SC_CREATED);
        assertTrue(response.getLocation().toString().contains(FAKE_KEY));
    }

    @Test
    public void postStudentBadParams() throws URISyntaxException {
        reset(mockStudentManager);
        Response response = underTest.create(0, null);
        assertEquals(response.getStatus(), HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void postStudentTestFailed() throws URISyntaxException {
        reset(mockStudentManager);
        doReturn(null).when(mockStudentManager).createStudent(any(Student.class), nullable(InputStream.class));

        Student createStudent = new Student(FAKE_ID, FAKE_NAME);
        Response response = underTest.create(0, createStudent);
        assertEquals(response.getStatus(), HttpStatus.SC_SERVICE_UNAVAILABLE);
    }

    @Test
    public void getStudentBadParams() throws URISyntaxException {
        Response response = underTest.get(0, null);
        assertEquals(response.getStatus(), HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void getStudentMissingTest() throws URISyntaxException {
        reset(mockStudentManager);
        doReturn(null).when(mockStudentManager).getStudent(anyString());

        Response response = underTest.get(0, FAKE_KEY);
        assertEquals(response.getStatus(), HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void getStudentFailedTest() throws URISyntaxException {
        /*
        reset(mockStudentManager);

        doReturn(null).when(mockStudentManager).getStudent(anyString());
        when(mockStudentManager.getStudents(anyString())).thenThrow(new RuntimeException("Fake Exception!"));

        Response response = underTest.getByName(FAKE_NAME, 0);
        assertEquals(response.getStatus(), HttpStatus.SC_SERVICE_UNAVAILABLE);
        */
    }

    @Test
    public void getStudentTest() throws URISyntaxException {
        reset(mockStudentManager);
        Student fakeStudent = new Student(FAKE_ID, FAKE_NAME);
        fakeStudent.setKey(FAKE_KEY);
        doReturn(fakeStudent).when(mockStudentManager).getStudent(anyString());

        Response response = underTest.get(0, FAKE_KEY);
        assertEquals(response.getStatus(), HttpStatus.SC_OK);

        // TODO: Add a check for the right headers once this gets sorted out in config
        Student responseStudent = (Student)response.getEntity();
        assertEquals(responseStudent.getKey(), FAKE_KEY);
        assertEquals(responseStudent.getId(), FAKE_ID);
        assertEquals(responseStudent.getName(), FAKE_NAME);
    }

    @Test
    public void getByNameFailedTest() throws URISyntaxException {
        /*
        reset(mockStudentManager);

        doReturn(null).when(mockStudentManager).getStudents(anyString());
        when(mockStudentManager.getStudents(anyString())).thenThrow(new RuntimeException("Fake Exception!"));

        Response response = underTest.getByName(FAKE_NAME, 0);
        assertEquals(response.getStatus(), HttpStatus.SC_SERVICE_UNAVAILABLE);
        */
    }

    @Test
    public void getStudentByName() throws URISyntaxException {
        reset(mockStudentManager);

        Student createStudent = new Student(FAKE_ID, FAKE_NAME);
        List<Student> studentList = new ArrayList<Student>();
        studentList.add(createStudent);
        doReturn(studentList).when(mockStudentManager).getStudents(FAKE_NAME);

        Response response = underTest.getByName(FAKE_NAME, 0);
        assertEquals(response.getStatus(), HttpStatus.SC_OK);
    }


}
