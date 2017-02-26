package com.davelabine.resterapp.platform.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import lombok.Getter;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

/**
 * Created by dave on 12/10/16.
 *
 */
public class AwsModule extends AbstractModule {

    @Override
    protected void configure() {
        // Add bindings here.
    }

    @Provides
    @Singleton
    AmazonS3Client getAmazonS3Client() {
        AmazonS3Client s3 = new AmazonS3Client(new ProfileCredentialsProvider());

        // TODO: set the region
            /*
        String regionStr = config.getString("s3.region");
        Region region = Region.fromValue(regionStr);
        s3.setRegion(region.toAWSRegion());
        */
        return s3;
    }
}
