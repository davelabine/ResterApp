package com.davelabine.resterapp.platform.api.model;

import com.davelabine.resterapp.platform.api.model.model.Student;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by dave on 10/2/16.
 */
public class StudentTest {

    @Test
    public void testCreate() {
        Student target = new Student("123456", "Joe Blough");
        assertEquals("123456", target.getId());
        assertEquals("Joe Blough", target.getName());
    }
}

