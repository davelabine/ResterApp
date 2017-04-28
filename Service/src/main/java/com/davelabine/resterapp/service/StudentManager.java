package com.davelabine.resterapp.service;

import com.davelabine.resterapp.platform.api.dao.DaoStudent;
import com.davelabine.resterapp.platform.api.model.Student;
import com.davelabine.resterapp.platform.api.service.BlobStoreService;
import com.davelabine.resterapp.platform.api.model.BlobData;
import com.davelabine.resterapp.platform.api.model.BlobLocation;

import javax.inject.Inject;

import java.io.InputStream;
import java.util.List;


/**
 * Created by davidl on 9/29/16.
 */
public class StudentManager {
    //TODO: Put this in config
    private String DEFAULT_PHOTO_URL = "https://static.pexels.com/photos/104827/cat-pet-animal-domestic-104827.jpeg";

    private DaoStudent daoStudent;
    private BlobStoreService blobStore;


    @Inject
    public StudentManager(DaoStudent daoStudent, BlobStoreService blobStoreService) {
        this.daoStudent = daoStudent;
        daoStudent.initialize();
        this.blobStore = blobStoreService;
    }

    // Returns the Key of the student, or null on failure
    public String createStudent(Student student, InputStream inputStream)
    {
        BlobLocation location = putPhoto(inputStream);
        if (inputStream != null) {
            student.setPhoto(location);
        }
        return daoStudent.createStudent(student);
    }


    // Returns the student at the key, or null on failure
    public Student getStudent(String studentKey) {
        // returns the value, or null on not found
        return daoStudent.getStudent(studentKey);
    }

    // Deletes a student
    public void deleteStudent(Student student) {
        daoStudent.deleteStudent(student);
    }

    // Returns a list of students in the DB that start with a prefix
    public List<Student> getStudents(String prefix) {
        return daoStudent.getStudentByName(prefix);
    }

    public void updateStudent(Student student) {
        daoStudent.updateStudent(student);
    }

    public String getPhotoUrl(BlobLocation location) {
        if (location == null) {
            return DEFAULT_PHOTO_URL;
        } else {
            return blobStore.getObjectUrl(location);
        }
    }

    private BlobLocation putPhoto(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }

        BlobData data = new BlobData(inputStream);
        return blobStore.putObject(data);
    }

    // A utility method to populate some fake data for testing
    public void populateFakeData(int numStudents) {
        for (int i=0; i < numStudents; i++) {
            createStudent(Student.randomStudent(), null);
        }
    }

}
