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
    private String id;

    @Getter
    @Setter
    private String name;

    public Student() {}

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringBuffer("[ id : ").append(this.id)
                .append(", name : ").append(this.name)
                .append(" ]").toString();
    }

}
