package com.davelabine.resterapp.integration.platform;


import com.davelabine.resterapp.platform.api.model.RandomStudent;
import com.davelabine.resterapp.platform.api.model.Student;
import org.hibernate.service.Service;
import org.junit.Before;
import org.junit.Test;

import lombok.Cleanup;

import com.davelabine.resterapp.platform.api.dao.DaoStudent;
import com.davelabine.resterapp.platform.dao.DaoStudentHbn;

import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.Transaction;
import org.hibernate.Query;


import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/*
 *
 * Created by davidl on 3/6/17.
 */
public class DaoStudentHbnItTest {
    private static final Configuration hbnConfig = new Configuration().configure("hibernate.cfg.xml");

    //TODO: Cleanup
    private ServiceRegistry hbnServiceRegistry;

    private DaoStudent daoStudent;


    @Before
    public void before() {
        SessionFactory sessionFactory = null;
        Session session = null;
        try {
            hbnServiceRegistry = new StandardServiceRegistryBuilder().applySettings(hbnConfig.getProperties()).build();
            daoStudent = new DaoStudentHbn(hbnConfig, hbnServiceRegistry);
            daoStudent.initialize();
        } catch (Exception e) {
            // Log an exception
        }
    }

    @Test
    public void testCRUDStudentTable() {
        // Test create and read
        Student student = new Student("666", "Mike Hunt");
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

        // Finally, delete the student
        String key = student.getKey();
        daoStudent.deleteStudent(student);
        Student deleteStudent = daoStudent.getStudent(key);
        assertNull(deleteStudent);
    }
}
