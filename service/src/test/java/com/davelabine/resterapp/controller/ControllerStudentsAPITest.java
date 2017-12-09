package com.davelabine.resterapp.controller;

import com.davelabine.resterapp.platform.api.exceptions.DaoException;
import com.davelabine.resterapp.platform.api.model.BlobData;
import com.davelabine.resterapp.platform.api.model.Student;
import com.davelabine.resterapp.service.StudentManager;
import static com.davelabine.resterapp.controller.ControllerStudentsAPI.*;
import static org.apache.http.HttpStatus.*;
import static javax.ws.rs.core.HttpHeaders.*;

import com.typesafe.config.Config;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.io.IOException;
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

    public static final String FAKE_SIZE = "1000";
    public static final String FAKE_SIZE_TOO_BIG = "3000";
    public static final long FAKE_SIZE_LIMIT = 2000; //bytes

    @InjectMocks
    private ControllerStudentsAPI underTest;

    @Mock
    private StudentManager mockStudentManager;

    @Mock
    private Config appConfig;

    @Mock
    private MultipartFormDataInput formDataInput;

    @Mock
    private HttpServletRequest request;

    @Mock
    private InputStream inputStream;

    @Before
    public void before() throws Exception {
    }

    private void setupStudentFormPost(String id, String name) throws IOException {
        reset(formDataInput);
        doReturn(FAKE_SIZE).when(request).getHeader(CONTENT_LENGTH);
        doReturn(FAKE_SIZE_LIMIT).when(appConfig).getLong("Api.max-photo-size");
        doReturn(id).when(formDataInput).getFormDataPart(FORM_STUDENT_ID, String.class, null);
        doReturn(name).when(formDataInput).getFormDataPart(FORM_STUDENT_NAME, String.class, null);
        doReturn(inputStream).when(formDataInput).getFormDataPart(FORM_STUDENT_PHOTO, InputStream.class, null);
    }

    @Test
    public void postCreateStudentSuccess() throws URISyntaxException, IOException {
        setupStudentFormPost(FAKE_ID, FAKE_NAME);
        reset(mockStudentManager);

        Student fakeStudent = new Student(FAKE_ID, FAKE_NAME);
        fakeStudent.setSkey(FAKE_KEY);
        doReturn(fakeStudent).when(mockStudentManager).createStudent(any(Student.class), any(BlobData.class));

        Response response = underTest.create(request, formDataInput, 0);
        assertEquals(response.getStatus(), SC_CREATED);
        assertTrue(response.getLocation().toString().contains(FAKE_KEY));

        Student responseStudent = (Student)response.getEntity();
        assertEquals(responseStudent.getSkey(), FAKE_KEY);
        assertEquals(responseStudent.getId(), FAKE_ID);
        assertEquals(responseStudent.getName(), FAKE_NAME);
    }


    @Test
    public void postCreateStudentBadParams() throws URISyntaxException, IOException {
        reset(mockStudentManager);

        // Don't bother testing an empty form.
        // Null params is done for us by the framework, just test other object business logic

        setupStudentFormPost(null, FAKE_NAME);
        Response response = underTest.create(request, formDataInput, 0);
        assertEquals(response.getStatus(), SC_BAD_REQUEST);

        setupStudentFormPost(FAKE_NAME, null);
        response = underTest.create(request, formDataInput, 0);
        assertEquals(response.getStatus(), SC_BAD_REQUEST);

        // Note: Null photo posted is OK... that means no photo was uploaded.
    }


    @Test
    public void postCreateStudentFailed() throws URISyntaxException, IOException {
        reset(mockStudentManager);
        doReturn(null).when(mockStudentManager).createStudent(any(Student.class), nullable(BlobData.class));

        setupStudentFormPost(FAKE_ID, FAKE_NAME);
        Response response = underTest.create(request, formDataInput, 0);
        assertEquals(response.getStatus(), SC_SERVICE_UNAVAILABLE);
    }

    @Test
    public void postCreateStudentPhotoTooBig() throws URISyntaxException, IOException {
        reset(mockStudentManager);

        setupStudentFormPost(FAKE_ID, FAKE_NAME);
        doReturn(FAKE_SIZE_TOO_BIG).when(request).getHeader(CONTENT_LENGTH);
        Response response = underTest.create(request, formDataInput, 0);
        assertEquals(response.getStatus(), SC_BAD_REQUEST);
    }

    /* Make sure we bubble up exceptions on this method */
    @Test(expected = DaoException.class)
    public void postCreateStudentException() throws URISyntaxException, IOException {
        reset(mockStudentManager);
        setupStudentFormPost(FAKE_ID, FAKE_NAME);

        when(mockStudentManager.createStudent(any(Student.class), nullable(BlobData.class)))
                                    .thenThrow(new DaoException("Fake Exception!"));
        underTest.create(request, formDataInput, 0);
    }

    @Test
    public void postUpdateStudentSuccess() throws URISyntaxException, IOException {
        setupStudentFormPost(FAKE_ID, FAKE_NAME);
        reset(mockStudentManager);

        Student fakeStudent = new Student(FAKE_ID, FAKE_NAME);
        fakeStudent.setSkey(FAKE_KEY);
        doReturn(fakeStudent).when(mockStudentManager).updateStudent(any(Student.class), any(BlobData.class));

        Response response = underTest.updateStudent(request, formDataInput, 0, FAKE_KEY);
        assertEquals(response.getStatus(), SC_OK);
        Student responseStudent = (Student)response.getEntity();
        assertEquals(responseStudent.getSkey(), FAKE_KEY);
        assertEquals(responseStudent.getId(), FAKE_ID);
        assertEquals(responseStudent.getName(), FAKE_NAME);
    }

    @Test
    public void postUpdateStudentBadParams() throws URISyntaxException, IOException {
        reset(mockStudentManager);
        // Null params is done for us by resteasy (integration test), just test other object business logic
        //Response response = underTest.create(0, null);

        setupStudentFormPost(null, FAKE_NAME);
        Response response = underTest.updateStudent(request, formDataInput, 0, FAKE_KEY);
        assertEquals(response.getStatus(), SC_BAD_REQUEST);

        setupStudentFormPost(FAKE_ID, null);
        response = underTest.updateStudent(request, formDataInput, 0, FAKE_KEY);
        assertEquals(response.getStatus(), SC_BAD_REQUEST);
    }

    /* Make sure we bubble up exceptions on this method */
    @Test(expected = DaoException.class)
    public void postUpdateStudentException() throws URISyntaxException, IOException {
        reset(mockStudentManager);
        Student student = Student.randomStudent();
        student.setSkey(FAKE_KEY);
        Mockito.doThrow(new DaoException("Fake Exception!")).when(mockStudentManager).updateStudent(student, null);
        mockStudentManager.updateStudent(student, null);
    }

    @Test
    public void getStudent() throws URISyntaxException {
        reset(mockStudentManager);
        Student fakeStudent = new Student(FAKE_ID, FAKE_NAME);
        fakeStudent.setSkey(FAKE_KEY);
        doReturn(fakeStudent).when(mockStudentManager).getStudent(anyString());

        Response response = underTest.get(0, FAKE_KEY);
        assertEquals(response.getStatus(), SC_OK);

        Student responseStudent = (Student)response.getEntity();
        assertEquals(responseStudent.getSkey(), FAKE_KEY);
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

    @Test
    public void deleteStudent() throws URISyntaxException {
        reset(mockStudentManager);
        Student fakeStudent = new Student(FAKE_ID, FAKE_NAME);
        fakeStudent.setSkey(FAKE_KEY);
        doReturn(fakeStudent).when(mockStudentManager).getStudent(anyString());

        Response response = underTest.deleteStudents(0, FAKE_KEY);
        assertEquals(response.getStatus(), SC_NO_CONTENT);
    }

    @Test
    public void deleteStudentMissing() throws URISyntaxException {
        reset(mockStudentManager);
        doReturn(null).when(mockStudentManager).getStudent(anyString());

        Response response = underTest.deleteStudents(0, FAKE_KEY);
        assertEquals(response.getStatus(), SC_NOT_FOUND);
    }

}
