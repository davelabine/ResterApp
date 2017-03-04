package com.davelabine.resterapp.module;

import com.davelabine.resterapp.platform.api.BlobStoreService;
import com.davelabine.resterapp.platform.blob.S3BlobStoreService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

/**
 * Created by dave on 12/10/16.
 *
 */
public class AwsModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BlobStoreService.class).to(S3BlobStoreService.class);
    }

    @Provides
    @Singleton
    AmazonS3Client getAmazonS3Client() {
        return new AmazonS3Client(new ProfileCredentialsProvider());
    }
}
