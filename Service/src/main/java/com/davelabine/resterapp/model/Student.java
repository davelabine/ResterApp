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
    private String key;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String urlPhoto;

    public Student() {}

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // TODO: shouldn't need this?!?!
    @Override
    public String toString() {
        return new StringBuffer("[ key : ").append(this.key)
                .append(", id : ").append(this.id)
                .append(", name : ").append(this.name)
                .append(", urlPhoto: ").append(this.urlPhoto)
                .append(" ]").toString();
    }

}
