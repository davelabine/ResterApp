package com.davelabine.resterapp.platform.dao;

import com.davelabine.resterapp.platform.api.dao.DaoStudent;
import com.davelabine.resterapp.platform.api.model.Student;
import com.davelabine.resterapp.platform.api.exceptions.DaoException;
import com.google.inject.Inject;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Created by davidl on 3/6/17.
 */
public class DaoStudentHbn implements DaoStudent {
    private static final Logger logger = LoggerFactory.getLogger(DaoStudentHbn.class);
    private final HbnTxManager hbnTxManager;


    @Inject
    public DaoStudentHbn(HbnTxManager hbnTxManager) {
        logger.info("constructor...");
        this.hbnTxManager = hbnTxManager;
    }

    /**
     * Initialize the object
     * @param
     * @return true if the table exists in the configured db, false otherwise.
     */
    @Override
    public void initialize() throws DaoException {
        hbnTxManager.initialize();
    }

    /**
     * Release resources used by the dao when finished
     * @param
     * @return
     */
    @Override
    public void close() {
        hbnTxManager.close();
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
    public String createStudent(Student student) {
        logger.info("createStudent {}", student);
        return hbnTxManager.processTx((studentTx, session) -> {
            session.save(studentTx);
            return studentTx.getSkey();
        }, student);
    }

    /**
     * Get the student associated with the provided key.
     * @param key of the student to get
     * @return A Student object, or null if Student is not found.
     */
    @Override
    public Student getStudent(String skey) {
        logger.info("getStudent {}", skey);
        return hbnTxManager.processTx((keyTx, session) -> {
            Query query = session.getNamedQuery("HQL_GET_STUDENT_BY_KEY");
            query.setString("skey", skey);
            return (Student) query.uniqueResult();
        }, skey);
    }

    /**
     * Get a list of students who match the provided name. (Name is not guaranteed to be unique)
     * @param A string prefix for the name to be queried for
     * @return A list of student objects, or null if no student was found.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Student> getStudentByName(String name) {
        logger.info("getStudentByName {}", name);
        return hbnTxManager.processTx((nameTx, session) -> {
            Query query = session.getNamedQuery("HQL_GET_STUDENT_BY_NAME_PARTIAL");
            query.setString("name", nameTx + "%");
            query.setMaxResults(100);
            return (List<Student>)query.list();
        }, name);
    }

    /**
     * Update the student data with the key of the student provided.
     * @param Student - the data and key of the student to update
     */
    @Override
    public void updateStudent(Student student) {
        logger.info("updateStudent {}", student);
        hbnTxManager.processTx((studentTx, session) -> {
            session.update(studentTx);
            return null;
        }, student);
    }

    /**
     * Delete a student referenced by the provided key.
     * @param  Student - the student to delete
     */
    @Override
    public void deleteStudent(Student student) {
        logger.info("deleteStudent {}",student);
        hbnTxManager.processTx((studentTx, session) -> {
            session.delete(studentTx);
            return null;
        }, student);
    }

}
