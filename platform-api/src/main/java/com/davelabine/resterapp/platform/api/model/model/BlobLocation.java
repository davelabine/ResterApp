package com.davelabine.resterapp.platform.api.model.model;

/**
 * Created by davidl on 2/21/17.
 */
public class BlobLocation {
    private String bucketName;
    private String key;

    private BlobLocation() {
    }

    private BlobLocation(Builder builder) {
        this.bucketName = builder.bucketName;
        this.key = builder.key;
    }

    public static Builder builder(String bucketName, String key) {
        return new Builder(bucketName, key);
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public String getKey() { return this.key; }

    /**
     * Builder for BlobLocation.
     */
    public static class Builder {

        private String bucketName;
        private String key;

        public Builder(String bucketName, String key) {
            this.bucketName = bucketName;
            this.key = key;
        }

        public BlobLocation build() {
            return new BlobLocation(this);
        }
    }
}
