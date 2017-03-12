package com.davelabine.resterapp.service;

import com.davelabine.resterapp.platform.api.dao.DaoStudent;
import com.davelabine.resterapp.platform.api.model.BlobLocation;
import com.davelabine.resterapp.platform.api.model.Student;

import com.davelabine.resterapp.platform.api.service.BlobStoreService;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * Created by dave on 10/1/16.
 */

@SuppressWarnings("unchecked")

@RunWith(MockitoJUnitRunner.class)
public class StudentManagerTest {
    public static final String FAKE_KEY = "FAKE_KEY";
    public static final String FAKE_ID = "FAKE_ID";
    public static final String FAKE_NAME = "FAKE_NAME";
    public static final String FAKE_BUCKET = "FAKE_BUCKET";
    public static final String FAKE_URL = "http://fake.url.com";

    @InjectMocks
    private StudentManager underTest;

    @Mock
    private DaoStudent mockDaoStudent;

    @Mock
    private BlobStoreService mockBlobStore;


    @Before
    public void before() {
    }

    // Test that a key is returned when creating a student
    @Test
    public void testCreateStudent() {
        reset(mockDaoStudent);

        /* TODO: make sure to add tests to cover generation of photo URL.  Mabye a separate PUT call.
        BlobLocation location = new BlobLocation.Builder(FAKE_BUCKET, FAKE_KEY).build();
        doReturn(location).when(mockBlobStore).putObject(any(BlobData.class));
        doReturn(FAKE_URL).when(mockBlobStore).getObjectUrl(any(BlobLocation.class));
        */

        Student student = new Student(FAKE_ID, FAKE_NAME);
        doReturn(FAKE_KEY).when(mockDaoStudent).createStudent(any(Student.class));
        String key = underTest.createStudent(student);

        assertNotNull(key);
        //Assert.assertNotNull(student.getUrlPhoto());

        // Used to verify unit tests are working correctly
        // Assert.assertEquals("fun", "icepick-in-eye");
    }

    // Test the case where a student cannot be found in the student list
    @Test
    public void testGetStudentMissing() {
        reset(mockDaoStudent);

        // test a student that isn't present in the student list
        doReturn(null).when(mockDaoStudent).getStudent(anyString());
        assertNull(underTest.getStudent(FAKE_KEY));
    }

    // Test a case where a student is found in the student list
    @Test
    public void testGetStudentFound() {
        reset(mockDaoStudent);

        Student mockStudent = new Student(FAKE_ID, FAKE_NAME);
        doReturn(mockStudent).when(mockDaoStudent).getStudent(FAKE_KEY);
        Student studentGet = underTest.getStudent(FAKE_KEY);
        assertEquals(FAKE_ID, studentGet.getId());
        assertEquals(FAKE_NAME, studentGet.getName());
    }
}
