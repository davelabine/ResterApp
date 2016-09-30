package com.davelabine.ResterApp;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by davidl on 9/29/16.
 */
public class Student {

    @Getter
    private String studentID;

    @Getter
    private String studentName;

    public Student(String studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;
    }

    public Student(String studentName) {
        this.studentID = UUID.randomUUID().toString();
        this.studentName = studentName;
    }

}
