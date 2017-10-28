package com.davelabine.resterapp.integration.platform;


import com.davelabine.resterapp.platform.api.exceptions.DaoException;
import com.davelabine.resterapp.platform.api.model.BlobLocation;
import com.davelabine.resterapp.platform.api.model.Student;
import com.davelabine.resterapp.platform.dao.HbnTxManager;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.davelabine.resterapp.platform.api.dao.DaoStudent;
import com.davelabine.resterapp.platform.dao.DaoStudentHbn;

import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.RuntimeErrorException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/*
 *
 * Created by davidl on 3/6/17.
 */
public class DaoStudentHbnItTest {
    private static final String DB_ENV_UNAME = "DB_ENV_UNAME";
    private static final String DB_ENV_PW = "DB_ENV_PW";

    private static final String FAKE_BUCKET = "FAKE-BUCKET";
    private static final String FAKE_KEY = "FAKE-KEY";

    private static final Logger logger = LoggerFactory.getLogger(DaoStudentHbnItTest.class);
    private static final Config config = ConfigFactory.load("secret.conf");

    private ServiceRegistry hbnServiceRegistry;
    private HbnTxManager hbnTxManager;
    private DaoStudent daoStudent;


    @Before
    public void before() {
        Configuration hbnConfig = new Configuration();
        hbnConfig.configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = null;
        Session session = null;

        String uname = config.getString("Secret.DB_UNAME");
        String pw = config.getString("Secret.DB_PW");
        if ( (uname == null) || (pw == null) ) {
            logger.error("Hibernate DB username ({}) or password ({}) are not set!  Check Secret.conf",
                    DB_ENV_UNAME, DB_ENV_PW);
            throw new RuntimeException("#### Need to set DB credentials!");
        }

        hbnConfig.setProperty("hibernate.connection.username", uname);
        hbnConfig.setProperty("hibernate.connection.password", pw);

        try {
            hbnServiceRegistry = new StandardServiceRegistryBuilder().applySettings(hbnConfig.getProperties()).build();
            hbnTxManager = new HbnTxManager(hbnConfig, hbnServiceRegistry);
            daoStudent = new DaoStudentHbn(hbnTxManager);
            daoStudent.initialize();
        } catch (Exception e) {
            logger.error("init exception: {}", e.getMessage());
        }
    }

    // TODO: determine when we close the TxManager
    /*
    @After
    public void after() {
        hbnTxManager.close();
    }*/

    @Test
    public void testCRUDStudentTable() {
        // Test create and read
        Student student = Student.randomStudent();
        student.setPhoto(new BlobLocation(FAKE_BUCKET, FAKE_KEY));

        daoStudent.createStudent(student);
        Student getStudent = daoStudent.getStudent(student.getSkey());
        assertEquals(student.getName(), getStudent.getName());

        // Now verify we can get by name
        List<Student> nameStudent = daoStudent.getStudentByName(student.getName());
        assertTrue(nameStudent.stream().anyMatch(item -> student.getName().equals(item.getName())));

        // Now verify we can update a student
        student.setName("Oderus Urungus");
        student.setId("123456");
        daoStudent.updateStudent(student);
        Student updateStudent = daoStudent.getStudent(student.getSkey());
        assertEquals(student.getName(), updateStudent.getName());
        assertEquals(student.getId(), updateStudent.getId());

        // Finally, delete the student
        String key = student.getSkey();
        daoStudent.deleteStudent(student);
        Student deleteStudent = daoStudent.getStudent(key);
        assertNull(deleteStudent);
    }


    @Test
    public void testGetNullStudent() {
        assertNull(daoStudent.getStudent("FAKE_KEY"));
    }

    @Test(expected = DaoException.class)
    public void testCreateNullStudent() {
        daoStudent.createStudent(null);
    }

    @Test(expected = DaoException.class)
    public void testUpdateNullStudent() {
        daoStudent.updateStudent(null);
    }

    @Test(expected = DaoException.class)
    public void testDeleteNullStudent() {
        daoStudent.deleteStudent(null);
    }

}
