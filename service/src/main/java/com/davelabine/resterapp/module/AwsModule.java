package com.davelabine.resterapp.module;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.davelabine.resterapp.platform.api.service.BlobStoreService;
import com.davelabine.resterapp.platform.service.S3BlobStoreService;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
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
    AmazonS3 getAmazonS3Client(@Named("aws.conf") final Config awsConfig,
                               @Named("secret.conf") final Config secretConfig) {
        BasicAWSCredentials creds = new BasicAWSCredentials(
                secretConfig.getString("Secret.AWS_ACCESS_KEY_ID"),
                secretConfig.getString("Secret.AWS_SECRET_ACCESS_KEY"));
        AWSStaticCredentialsProvider credProvider = new AWSStaticCredentialsProvider(creds);

        return AmazonS3Client.builder()
                .withCredentials(credProvider)
                .withRegion(awsConfig.getString("s3.region"))
                .build();
    }
}
