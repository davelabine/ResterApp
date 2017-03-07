package com.davelabine.resterapp.integration.platform;

import com.davelabine.resterapp.platform.api.model.model.BlobData;
import com.davelabine.resterapp.platform.api.model.model.BlobLocation;
import com.davelabine.resterapp.platform.api.model.service.BlobStoreService;
import com.davelabine.resterapp.platform.service.*;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by dave on 12/10/16.
 */

public class BlobStoreServiceItTest {
    private static String UPLOAD_FILE_NAME = "images/DSC_0133.jpg";

    private static final Config awsConfig = ConfigFactory.load("aws.conf");

    BlobStoreService blobStore;

    @Before
    public void before() {
        AmazonS3Client s3 = new AmazonS3Client(new ProfileCredentialsProvider());
        blobStore = new S3BlobStoreService(s3, awsConfig);
    }

    @Test
    public void testPutObjectAndGetUrl() {
        String fileName = Thread.currentThread().getContextClassLoader().getResource(UPLOAD_FILE_NAME).getPath();
        BlobData data = new BlobData(fileName);
        BlobLocation location = blobStore.putObject(data);
        Assert.assertNotNull(location);

        String url = blobStore.getObjectUrl(location);
        Assert.assertNotNull(url);
    }
}
