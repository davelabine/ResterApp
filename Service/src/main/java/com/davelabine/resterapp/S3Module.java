package com.davelabine.resterapp;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

//import com.amazonaws.services.s3.AmazonS3Client;

/**
 * Created by dave on 12/10/16.
 *
 */
public class S3Module extends AbstractModule {

    @Override
    protected void configure() {
        // Add bindings here.
    }
    /* TODO: setup AWS dependencies correctly.
    @Provides
    @Singleton
    AmazonS3Client getAmazonS3Client() {
        // Config mechanism described here:
        // http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
        // Make sure env variables are set:
        // AWS_ACCESS_KEY_ID
        // AWS_SECRET_ACCESS_KEY
        AmazonS3Client s3 = new AmazonS3Client();

        // TODO: set the region
        String regionStr = config.getString("s3.region");
        Region region = Region.fromValue(regionStr);
        s3.setRegion(region.toAWSRegion());
        return s3;
    }
    */
}
