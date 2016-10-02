package com.davelabine.ResterApp;

import javax.inject.Inject;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;



/**
 * Created by davidl on 9/29/16.
 */
public class StudentManager {

    //private HashMap<String, Student> studentMap;
    private ConcurrentHashMap<String, Student> studentMap;

    @Inject
    public StudentManager() {
        studentMap = new ConcurrentHashMap<String, Student>();
    }

    // Returns the Key of the student, or null on failure
    public String createStudent(String studentID, String studentName) {

        // TODO handle bad inputs

        String studentKey = UUID.randomUUID().toString();
        Student studentAdd = new Student(studentID, studentName);

        // Returns previous value if another value was mapped to same key
        // or null if there was no key mapping
        // and throws null pointer exceptions if key or value are empty
        studentMap.put(studentKey, studentAdd);

        return studentKey;
    }

    // Returns the student at the key, or null on failure
    public Student getStudent(String studentKey) {
        // returns the value, or null on not found
        return studentMap.get(studentKey);
    }

}
