package com.davelabine.resterapp.service;

import com.davelabine.resterapp.platform.api.dao.DaoStudent;
import com.davelabine.resterapp.platform.api.model.Student;
import com.davelabine.resterapp.platform.api.service.BlobStoreService;

import javax.inject.Inject;

import java.util.List;


/**
 * Created by davidl on 9/29/16.
 */
public class StudentManager {
    private static String UPLOAD_FILE_NAME = "images/dave_monkey.jpg";

    private DaoStudent daoStudent;
    private BlobStoreService blobStore;


    @Inject
    public StudentManager(DaoStudent daoStudent, BlobStoreService blobStoreService) {
        this.daoStudent = daoStudent;
        daoStudent.initialize();
        this.blobStore = blobStoreService;
    }

    // Returns the Key of the student, or null on failure
    public String createStudent(Student student)
    {
        // Add a profile pic
        // TODO: Move this into its own put method
        /*
        String fileName = Thread.currentThread().getContextClassLoader().getResource(UPLOAD_FILE_NAME).getPath();
        BlobData data = new BlobData(fileName);
        BlobLocation location = blobStore.putObject(data);
        if (location != null ) {
            String url = blobStore.getObjectUrl(location);
            student.setUrlPhoto(url);
        }
        */
        return daoStudent.createStudent(student);
    }


    // Returns the student at the key, or null on failure
    public Student getStudent(String studentKey) {
        // returns the value, or null on not found
        return daoStudent.getStudent(studentKey);
    }

    // Returns a list of students in the DB that start with a prefix
    public List<Student> getStudents(String prefix) {
        return daoStudent.getStudentByName(prefix);
    }

    // A throwaway method to populate a bit of fake data for testing
    // TODO: Remove me
    public void populateFakeData() {
        for (int i=0; i < 10; i++) {
            createStudent(Student.randomStudent());
        }
    }

}
