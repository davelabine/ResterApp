package com.davelabine.resterapp.integration.platform;


import com.davelabine.resterapp.platform.api.model.RandomStudent;
import com.davelabine.resterapp.platform.api.model.Student;
import org.junit.Before;
import org.junit.Test;

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
    private Configuration configHbn;


    DaoStudent daoStudent;


    @Before
    public void before() {

        SessionFactory sessionFactory = null;
        Session session = null;
        try {
            configHbn = new Configuration();
            configHbn.configure("hibernate.cfg.xml");
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configHbn.getProperties()).build();
            sessionFactory = configHbn.buildSessionFactory(serviceRegistry);
            session = sessionFactory.openSession();

            // Create and add a student
            // TODO: figure out if we need to commit like this....
            Transaction transaction = session.getTransaction();
            Student student = new Student("123456", "Billy Bob");
            transaction.begin();
            session.save(student);
            transaction.commit();

            // Query for a student
            String hql = "from Student where id = :id";
            Query query = session.createQuery(hql);
            query.setString("id", "123456");
            List<Student> results = (List<Student>)query.list();
            Iterator<Student> it = results.iterator();
            while (it.hasNext()) {
                Student itStudent = it.next();
            }


            // Now go and find that student with a query

        } catch (Exception e) {
            // Log an exception
            int i=0;
        } finally {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            if (session != null) {
                session.close();
            }
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }

    }

    @Test
    public void testCRUDStudentTable() {

        /*
        daoStudent = new DaoStudentHbn(sqlConfig);
        daoStudent.initialize();
        daoStudent.close();

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
