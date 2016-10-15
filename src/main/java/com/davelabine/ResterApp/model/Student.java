package com.davelabine.resterapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;

/**
 * Created by davidl on 9/29/16.
 */
public class Student {

    @Getter
    @Setter
    private String studentID;

    @Getter
    @Setter
    private String studentName;

    public Student() {}

    public Student(String studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;
    }

}
