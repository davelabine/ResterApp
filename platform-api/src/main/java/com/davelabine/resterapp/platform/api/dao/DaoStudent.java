package com.davelabine.resterapp.platform.api.dao;

import com.davelabine.resterapp.platform.api.model.Student;

import java.util.List;

/**
 * Created by davidl on 3/6/17.
 */
public interface DaoStudent {

    /**
     * Confirm the student table exists.
     * @param
     * @return true if the table exists in the configured db, false otherwise.
     */
    boolean tableExists();


    /**
     * Initialize the dao
     * @param
     * @return true if the dao was initialized, false otherwise
     */
    boolean initialize();

    /**
     * Release resources used by the dao when finished
     * @param
     * @return
     */
    void close();

    /**
     * Create a new student in the dao
     * @param Student object to create
     * @return  the key of the created student object
     */
    String createStudent(Student student);

    /**
     * Get the student associated with the provided key.
     * @param
     * @return A Student object, or false if the student is not found.
     */
    Student getStudent(String key);

    /**
     * Get a list of students who match the provided name. (Name is not guaranteed to be unique)
     * @param
     * @return A list of student objects, or null if no student was found.
     */
    List<Student> getStudentByName(String name);

    /**
     * Update the student data with the key of the student provided.
     * @param Student - the data and key of the student to update
     * @return true if student was updated, false otherwise.
     */
    boolean updateStudent(Student student);

    /**
     * Delete a student referenced by the provided key.
     * @param  key of the student to delete
     * @return  true if student was deleted, false otherwise.
     */
    boolean deleteStudent(Student student);
}