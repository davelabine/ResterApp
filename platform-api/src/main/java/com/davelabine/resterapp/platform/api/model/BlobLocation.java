package com.davelabine.resterapp.platform.api.model;

import lombok.Getter;
import javax.persistence.*;


/**
 * Created by davidl on 2/21/17.
 */
@Embeddable
@Getter
public class BlobLocation {
    @Column(name="BUCKET_NAME")
    private String bucketName;
    @Column(name="BUCKET_KEY")
    private String key;

    public BlobLocation() {}

    public BlobLocation(String bucketName, String key) {
        this.bucketName = bucketName;
        this.key = key;
    }
}
