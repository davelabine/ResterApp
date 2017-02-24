package com.davelabine.resterapp.service;

import com.davelabine.resterapp.model.Student;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;

/**
 * Created by dave on 10/1/16.
 */

@SuppressWarnings("unchecked")

@RunWith(MockitoJUnitRunner.class)
public class StudentManagerTest {
    public static final String FAKE_KEY = "FAKE_KEY";
    public static final String FAKE_ID = "FAKE_ID";
    public static final String FAKE_NAME = "FAKE_NAME";

    @InjectMocks
    private StudentManager underTest;

    @Mock
    private ConcurrentHashMap<String, Student> mockStudentMap;


    @Before
    public void before() {
    }

    // Test that a key is returned when creating a student
    @Test
    public void testCreateStudent() {
        reset(mockStudentMap);

        Student student = new Student(FAKE_ID, FAKE_NAME);
        String key = underTest.createStudent(student);
        Assert.assertNotNull(key);

        // Used to verify unit tests are working correctly
        // Assert.assertEquals("fun", "icepick-in-eye");
    }

    // Test the case where a student cannot be found in the student list
    @Test
    public void testGetStudentMissing() {
        reset(mockStudentMap);

        // test a student that isn't present in the student list
        doReturn(null).when(mockStudentMap).get(anyString());
        Assert.assertNull(underTest.getStudent(FAKE_KEY));
    }

    // Test a case where a student is found in the student list
    @Test
    public void testGetStudentFound() {
        reset(mockStudentMap);

        Student mockStudent = new Student(FAKE_ID, FAKE_NAME);
        doReturn(mockStudent).when(mockStudentMap).get(FAKE_KEY);
        Student studentGet = underTest.getStudent(FAKE_KEY);
        Assert.assertEquals(FAKE_ID, studentGet.getId());
        Assert.assertEquals(FAKE_NAME, studentGet.getName());
    }
}
