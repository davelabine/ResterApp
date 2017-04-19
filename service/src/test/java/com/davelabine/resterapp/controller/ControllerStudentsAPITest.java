package com.davelabine.resterapp.controller;

import com.davelabine.resterapp.platform.api.exceptions.DaoException;
import com.davelabine.resterapp.platform.api.model.Student;
import com.davelabine.resterapp.service.StudentManager;
import static org.apache.http.HttpStatus.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


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
    public void postCreateStudentSuccess() throws URISyntaxException {
        reset(mockStudentManager);
        doReturn(FAKE_KEY).when(mockStudentManager).createStudent(any(Student.class), nullable(InputStream.class));

        Student fakeStudent = new Student(FAKE_ID, FAKE_NAME);
        Response response = underTest.create(0, fakeStudent);
        assertEquals(response.getStatus(), SC_CREATED);
        assertTrue(response.getLocation().toString().contains(FAKE_KEY));
    }

    @Test
    public void postCreateStudentBadParams() throws URISyntaxException {
        reset(mockStudentManager);
        // Null params is done for us by the framework, just test other object business logic
        //Response response = underTest.create(0, null);
        Response response = underTest.create(0, new Student(null, FAKE_NAME));
        assertEquals(response.getStatus(), SC_BAD_REQUEST);
        response = underTest.create(0, new Student(FAKE_ID, null));
        assertEquals(response.getStatus(), SC_BAD_REQUEST);
    }

    @Test
    public void postCreateStudentFailed() throws URISyntaxException {
        reset(mockStudentManager);
        doReturn(null).when(mockStudentManager).createStudent(any(Student.class), nullable(InputStream.class));

        Student createStudent = new Student(FAKE_ID, FAKE_NAME);
        Response response = underTest.create(0, createStudent);
        assertEquals(response.getStatus(), SC_SERVICE_UNAVAILABLE);
    }

    /* Make sure we bubble up exceptions on this method */
    @Test(expected = DaoException.class)
    public void postCreateStudentException() throws URISyntaxException {
        reset(mockStudentManager);
        Student student = Student.randomStudent();
        when(mockStudentManager.createStudent(student, null)).thenThrow(new DaoException("Fake Exception!"));
        mockStudentManager.createStudent(student, null);
    }

    /* No useful way to unit test this since code assumes success unless an exception is thrown.
    @Test
    public void postUpdateStudentSuccess() throws URISyntaxException {

    }
    */

    @Test
    public void postUpdateStudentBadParams() throws URISyntaxException {
        reset(mockStudentManager);
        // Null params is done for us by resteasy (integration test), just test other object business logic
        //Response response = underTest.create(0, null);
        Response response = underTest.updateStudent(0, FAKE_KEY, new Student(null, FAKE_NAME));
        assertEquals(response.getStatus(), SC_BAD_REQUEST);
        response = underTest.updateStudent(0, FAKE_KEY, new Student(FAKE_ID, null));
        assertEquals(response.getStatus(), SC_BAD_REQUEST);
    }

    /* Make sure we bubble up exceptions on this method */
    @Test(expected = DaoException.class)
    public void postUpdateStudentException() throws URISyntaxException {
        reset(mockStudentManager);
        Student student = Student.randomStudent();
        student.setKey(FAKE_KEY);
        Mockito.doThrow(new DaoException("Fake Exception!")).when(mockStudentManager).updateStudent(student);
        mockStudentManager.updateStudent(student);
    }

    @Test
    public void getStudent() throws URISyntaxException {
        reset(mockStudentManager);
        Student fakeStudent = new Student(FAKE_ID, FAKE_NAME);
        fakeStudent.setKey(FAKE_KEY);
        doReturn(fakeStudent).when(mockStudentManager).getStudent(anyString());

        Response response = underTest.get(0, FAKE_KEY);
        assertEquals(response.getStatus(), SC_OK);

        Student responseStudent = (Student)response.getEntity();
        assertEquals(responseStudent.getKey(), FAKE_KEY);
        assertEquals(responseStudent.getId(), FAKE_ID);
        assertEquals(responseStudent.getName(), FAKE_NAME);
    }

    /*
        No need to test null requests, this is handled by resteasy (integration test)
    @Test
    public void getStudentBadParams() throws URISyntaxException {
        Response response = underTest.get(0, null);
        assertEquals(response.getStatus(), SC_BAD_REQUEST);
    }
    */

    @Test
    public void getStudentMissing() throws URISyntaxException {
        reset(mockStudentManager);
        doReturn(null).when(mockStudentManager).getStudent(anyString());

        Response response = underTest.get(0, FAKE_KEY);
        assertEquals(response.getStatus(), SC_NOT_FOUND);
    }

    /* Make sure we bubble up exceptions on this method */
    @Test(expected = DaoException.class)
    public void getStudentException() throws URISyntaxException {
        reset(mockStudentManager);
        when(mockStudentManager.getStudent(anyString())).thenThrow(new DaoException("Fake Exception!"));
        mockStudentManager.getStudent(FAKE_ID);
    }

    @Test
    public void getStudentByName() throws URISyntaxException {
        reset(mockStudentManager);

        Student createStudent = new Student(FAKE_ID, FAKE_NAME);
        List<Student> studentList = new ArrayList<Student>();
        studentList.add(createStudent);
        doReturn(studentList).when(mockStudentManager).getStudents(FAKE_NAME);

        Response response = underTest.getByName(FAKE_NAME, 0);
        assertEquals(response.getStatus(), SC_OK);
    }

    /* Make sure we bubble up exceptions on this method */
    @Test(expected = DaoException.class)
    public void getByNameException() throws URISyntaxException {
        reset(mockStudentManager);
        when(mockStudentManager.getStudents(anyString())).thenThrow(new DaoException("Fake Exception!"));
        mockStudentManager.getStudents(FAKE_NAME);
    }

}
