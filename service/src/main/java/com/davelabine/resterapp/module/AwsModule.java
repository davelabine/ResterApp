package com.davelabine.resterapp.module;

import com.amazonaws.services.s3.AmazonS3Client;
import com.davelabine.resterapp.platform.api.service.BlobStoreService;
import com.davelabine.resterapp.platform.service.S3BlobStoreService;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.google.inject.name.Named;
import com.typesafe.config.Config;

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
    @Inject
    AmazonS3 getAmazonS3Client(@Named("aws.conf") final Config awsConfig) {
        return AmazonS3Client.builder()
                .withCredentials(new ProfileCredentialsProvider())
                .withRegion(awsConfig.getString("s3.region"))
                .build();
    }
}
