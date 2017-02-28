package com.davelabine.resterapp.service;

import com.davelabine.resterapp.model.Student;
import com.davelabine.resterapp.platform.api.BlobStoreService;

import javax.inject.Inject;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;



/**
 * Created by davidl on 9/29/16.
 */
public class StudentManager {
    private ConcurrentHashMap<String, Student> studentMap;
    private BlobStoreService blobStoreService;


    @Inject
    public StudentManager(ConcurrentHashMap<String, Student> studentMap, BlobStoreService blobStoreService) {
        this.studentMap = studentMap;
        this.blobStoreService = blobStoreService;
    }

    // Returns the Key of the student, or null on failure
    public String createStudent(Student student)
    {
        // TODO handle bad inputs
        String studentKey = UUID.randomUUID().toString();

        // Returns previous value if another value was mapped to same key
        // or null if there was no key mapping
        // and throws null pointer exceptions if key or value are empty
        studentMap.put(studentKey, student);

        return studentKey;
    }


    // Returns the student at the key, or null on failure
    public Student getStudent(String studentKey) {
        // returns the value, or null on not found
        return studentMap.get(studentKey);
    }

}
