package com.davelabine.resterapp.platform.api.model.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by davidl on 9/29/16.
 */
public class Student {

    @Getter
    @Setter
    protected String key;

    @Getter
    @Setter
    protected String id;

    @Getter
    @Setter
    protected String name;

    @Getter
    @Setter
    protected BlobLocation photo;

    public Student() {}

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
