package com.davelabine.resterapp.platform.api.model.model;

import lombok.Getter;

@Getter

/**
 * Created by davidl on 2/21/17.
 */
public class BlobData {
    private String fileName;

    public BlobData(String fileName) {
        this.fileName = fileName;
    }

}
