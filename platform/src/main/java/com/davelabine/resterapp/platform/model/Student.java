package com.davelabine.resterapp.platform.model;

import com.davelabine.resterapp.platform.api.BlobLocation;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;

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
