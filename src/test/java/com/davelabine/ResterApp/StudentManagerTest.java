package com.davelabine.resterapp;

import com.davelabine.resterapp.service.StudentManager;
import com.davelabine.resterapp.model.Student;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by dave on 10/1/16.
 */
public class StudentManagerTest {
    private StudentManager target;

    @Before
    public void setUp() throws Exception {
        target = new StudentManager();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCreateStudent() {
        String key = target.createStudent("123456", "Jimbo Jones");

        Student student = target.getStudent(key);
        assertEquals("123456", student.getStudentID());
        assertEquals("Jimbo Jones", student.getStudentName());
    }
}
