package com.davelabine.resterapp.integration.platform;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import org.junit.Before;
import org.junit.Test;

import com.davelabine.resterapp.platform.api.model.dao.DaoStudent;
import com.davelabine.resterapp.platform.api.model.model.RandomStudent;
import com.davelabine.resterapp.platform.api.model.model.Student;
import com.davelabine.resterapp.platform.dao.DaoStudentSQL;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/*
 *
 * Created by davidl on 3/6/17.
 */
public class DaoStudentSQLItTest {
    private static final Config sqlConfig = ConfigFactory.load("SQL.conf");

    DaoStudent daoStudent;


    @Before
    public void before() {

    }

    @Test
    public void testCRUDStudentTable() {
        daoStudent = new DaoStudentSQL(sqlConfig);
        daoStudent.initialize();
        daoStudent.close();
        /*
        daoStudent.createTable();

        // Test create and read
        Student student = new RandomStudent();
        daoStudent.createStudent(student);
        Student getStudent = daoStudent.getStudent(student.getKey());

        assertEquals(student.getName(), getStudent.getName());

        // Now verify we can get by name
        List<Student> nameStudent = daoStudent.getStudentByName(student.getName());
        assertTrue(nameStudent.stream().anyMatch(item -> student.getName().equals(item.getName())));

        // Now verify we can update a student
        student.setName("Oderus Urungus");
        student.setId("123456");
        daoStudent.updateStudent(student);
        Student updateStudent = daoStudent.getStudent(student.getKey());
        assertEquals(student.getName(), updateStudent.getName());
        assertEquals(student.getId(), updateStudent.getId());

        // Finally, delete the item
        daoStudent.deleteStudent(student.getKey());
        Student deleteStudent = daoStudent.getStudent(student.getKey());
        assertNull(deleteStudent);
        */
    }
}
