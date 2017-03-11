package com.davelabine.resterapp.platform.dao;

import com.davelabine.resterapp.platform.api.dao.DaoStudent;
import com.davelabine.resterapp.platform.api.model.Student;
import com.davelabine.resterapp.platform.api.exceptions.DaoException;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.typesafe.config.Config;
import lombok.Builder;
import lombok.Cleanup;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by davidl on 3/6/17.
 */
public class DaoStudentHbn implements DaoStudent {
    private static final Logger logger = LoggerFactory.getLogger(DaoStudentHbn.class);

    private final Configuration hbnConfig;
    private final ServiceRegistry hbnRegistry;
    SessionFactory hbnSessionFactory;

    // I need to define these variables with almost every transaction and handle setup and teardown in almost
    // exactly the same way.  Member variables feel a little icky, but seem like the best solution.
    Session session = null;
    Transaction transaction = null;

    @Inject
    public DaoStudentHbn(Configuration hbnConfig, ServiceRegistry hbnRegistry) {
        logger.info("constructor...");
        this.hbnConfig = hbnConfig;
        this.hbnRegistry = hbnRegistry;
    }

    /**
     * Initialize the object
     * @param
     * @return true if the table exists in the configured db, false otherwise.
     */
    @Override
    public void initialize() throws DaoException {
        logger.info("Intialize");
        try {
            hbnSessionFactory = hbnConfig.buildSessionFactory(hbnRegistry);
        } catch (HibernateException e) {
            throw new DaoException(("Initialize failed!"), e);
        }
    }

    /**
     * Release resources used by the dao when finished
     * @param
     * @return
     */
    @Override
    public void close() {
        hbnSessionFactory.close();
    }

    /**
     * Helper method to simplify repetitive transaction code.
     * I tried a lot of fancy template-based handling and it was complicated and not very readable.
     * This seems to do the trick.
     * @param
     * @return
     */

    private void startTransaction() throws HibernateException {
        try {
            session = hbnSessionFactory.getCurrentSession();
            transaction = session.getTransaction();
            transaction.begin();
        } catch (HibernateException e) {
            throw new DaoException("Can't start a transaction", e);
        }
        // Session automatically closed
    }

    /**
     * Confirm the student table exists.
     * @param
     * @return true if the table exists in the configured db, false otherwise.
     */
    @Override
    public boolean tableExists() {
        return true;
    }


    /**
     * Put an object into the blobstore and retrieve the BlobLocation.
     * @param Student object to create
     * @return  the key of the created student object
     */
    @Override
    public String createStudent(Student student) throws DaoException {
        logger.info("createStudent {}", student);

        try {
            startTransaction();
            session.save(student);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction.isActive()) { transaction.rollback(); }
            throw new DaoException("Can't create student: " + student.toString(), e);
        }

        return student.getKey();
    }

    /**
     * Get the student associated with the provided key.
     * @param
     * @return A Student object, or false if the student is not found.
     */
    @Override
    public Student getStudent(String key) throws DaoException {
        logger.info("getStudent {}", key);
        Student getStudent = null;
        try {
            startTransaction();
            Query query = session.getNamedQuery("HQL_GET_STUDENT_BY_KEY");
            query.setString("key", key);
            getStudent = (Student)query.uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction.isActive()) { transaction.rollback(); }
            throw new DaoException("Can't get student: " + key, e);
        }
        return getStudent;
    }

    /**
     * Get a list of students who match the provided name. (Name is not guaranteed to be unique)
     * @param
     * @return A list of student objects, or null if no student was found.
     */
    @Override
    @SuppressWarnings("unchecked") // due to query.list() cast required
    public List<Student> getStudentByName(String name) throws DaoException {
        logger.info("getStudentByName {}", name);
        List<Student> list = null;
        try {
            startTransaction();
            Query query = session.getNamedQuery("HQL_GET_STUDENT_BY_NAME_PARTIAL");
            query.setString("name", name + "%");
            query.setMaxResults(10);
            list = (List<Student>)query.list();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction.isActive()) { transaction.rollback(); }
            throw new DaoException("Can't get student by name: " + name, e);
        }
        return list;
    }

    /**
     * Update the student data with the key of the student provided.
     * @param Student - the data and key of the student to update
     * @return true if student was updated, false otherwise.
     */
    @Override
    public void updateStudent(Student student) throws DaoException {
        logger.info("updateStudent {}", student);

        try {
            startTransaction();
            session.update(student);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction.isActive()) { transaction.rollback(); }
            throw new DaoException("Can't update student: " + student.toString(), e);
        }
    }

    /**
     * Delete a student referenced by the provided key.
     * @param  key of the student to delete
     * @return  true if student was deleted, false otherwise.
     */
    @Override
    public void deleteStudent(Student delete) throws DaoException {
        logger.info("delete Student {}", delete);

        try {
            startTransaction();
            session.delete(delete);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction.isActive()) { transaction.rollback(); }
            throw new DaoException("Can't delete student: " + delete.toString(), e);
        }
    }
}
