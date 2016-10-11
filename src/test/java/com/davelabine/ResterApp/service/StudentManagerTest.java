package com.davelabine.resterapp.service;

import com.davelabine.resterapp.model.Student;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;

/**
 * Created by dave on 10/1/16.
 */
public class StudentManagerTest {

    @InjectMocks
    private StudentManager target;

    @Test
    public void testCreateStudent() {
        Student student = new Student("123456", "Jimbo Jones");
        String key = target.createStudent(student);

        Student studentGet = target.getStudent(key);
        Assert.assertEquals("123456", studentGet.getStudentID());
        Assert.assertEquals("Jimbo Jones", studentGet.getStudentName());

        // Used to verify unit tests are working correctly
        // Assert.assertEquals("fun", "icepick-in-eye");
    }
}
