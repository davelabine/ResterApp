package com.davelabine.resterapp.platform.dao;

import com.davelabine.resterapp.platform.api.dao.DaoStudent;
import com.davelabine.resterapp.platform.api.model.Student;
import com.google.inject.name.Named;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by davidl on 3/6/17.
 */
public class DaoStudentHbn implements DaoStudent {
    private static final Logger logger = LoggerFactory.getLogger(DaoStudentHbn.class);

    /*
    private final Config sqlConfig;
    private final String studentTable; // A convenience
    */

    public DaoStudentHbn() {
        logger.info("constructor...");

    }

    /**
     * Initialize the object
     * @param
     * @return true if the table exists in the configured db, false otherwise.
     */
    @Override
    public boolean initialize() {

        return true;
    }

    /**
     * Release resources used by the dao when finished
     * @param
     * @return
     */
    @Override
    public void close() {

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

        /*
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement("INSERT INTO " + studentTable + " VALUES(?)");
            pst.setString(1, author);
            pst.executeUpdate();
        }
        */

        return null;
    }

    /**
     * Get the student associated with the provided key.
     * @param
     * @return A Student object, or false if the student is not found.
     */
    @Override
    public Student getStudent(String key) {
        return null;
    }

    /**
     * Get a list of students who match the provided name. (Name is not guaranteed to be unique)
     * @param
     * @return A list of student objects, or null if no student was found.
     */
    @Override
    public List<Student> getStudentByName(String name) {
        return null;
    }

    /**
     * Update the student data with the key of the student provided.
     * @param Student - the data and key of the student to update
     * @return true if student was updated, false otherwise.
     */
    @Override
    public boolean updateStudent(Student student) {

        return true;
    }

    /**
     * Delete a student referenced by the provided key.
     * @param  key of the student to delete
     * @return  true if student was deleted, false otherwise.
     */
    @Override
    public boolean deleteStudent(String key) {

        return true;
    }
}