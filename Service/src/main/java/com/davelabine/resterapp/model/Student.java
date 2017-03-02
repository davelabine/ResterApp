package com.davelabine.resterapp.model;

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
    private String key;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private BlobLocation photo;

    public Student() {}

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
