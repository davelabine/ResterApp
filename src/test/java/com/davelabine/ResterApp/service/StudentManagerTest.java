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

/**
 * Created by dave on 10/1/16.
 */

public class StudentManagerTest {

    @Inject
    private StudentManager target;


    @Test
    public void testCreateStudent() {

        /* TODO: Fix this test.  I don't have something right with DI

        Student student = new Student("123456", "Jimbo Jones");
        String key = target.createStudent(student);

        Student studentGet = target.getStudent(key);
        Assert.assertEquals("123456", studentGet.getStudentID());
        Assert.assertEquals("Jimbo Jones", studentGet.getStudentName());
        */
        // Used to verify unit tests are working correctly
        // Assert.assertEquals("fun", "icepick-in-eye");
    }
}
