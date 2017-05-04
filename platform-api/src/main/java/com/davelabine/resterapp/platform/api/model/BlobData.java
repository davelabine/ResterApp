package com.davelabine.resterapp.platform.api.model;

import lombok.Getter;

import java.io.InputStream;

@Getter

/**
 * Created by davidl on 2/21/17.
 */
public class BlobData {
    private InputStream inputStream;
    private long        contentLength;

    public BlobData(InputStream inputStream, long contentLength) {
        this.inputStream = inputStream;
        this.contentLength = contentLength;
    }

}
