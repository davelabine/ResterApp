package com.davelabine.resterapp.service;

import com.davelabine.resterapp.controller.ControllerRoster;
import com.davelabine.resterapp.model.Student;

import com.google.inject.Inject;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dave on 10/1/16.
 */

public class StudentManagerTest {

    private StudentManager underTest;

    // Nope, JUnit says test class should have exactly one zero-argument constructor
    /*
    @Inject
    public StudentManagerTest(StudentManager target){
        this.target = target;
    }
    */

    /*
        The Convention seems to be to use something like @Before instead of @Inject
     */
    @Before
    public void before() {
        // Could mock this out if it were more complicated.
        underTest = new StudentManager();
    }



    @Test
    public void testCreateStudent() {

        Student student = new Student("123456", "Jimbo Jones");
        String key = underTest.createStudent(student);

        Student studentGet = underTest.getStudent(key);
        Assert.assertEquals("123456", studentGet.getStudentID());
        Assert.assertEquals("Jimbo Jones", studentGet.getStudentName());

        // Used to verify unit tests are working correctly
        // Assert.assertEquals("fun", "icepick-in-eye");
    }
}
