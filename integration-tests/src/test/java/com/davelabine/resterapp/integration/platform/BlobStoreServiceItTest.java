package com.davelabine.resterapp.integration.platform;

import com.davelabine.resterapp.platform.api.exceptions.BlobStoreException;
import com.davelabine.resterapp.platform.api.model.BlobData;
import com.davelabine.resterapp.platform.api.model.BlobLocation;
import com.davelabine.resterapp.platform.api.service.BlobStoreService;
import com.davelabine.resterapp.platform.service.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.*;

/**
 * Created by dave on 12/10/16.
 */

public class BlobStoreServiceItTest {
    private static String UPLOAD_FILE_NAME = "images/DSC_0133.jpg";

    private static final Config awsConfig = ConfigFactory.load("aws.conf");

    BlobStoreService blobStore;

    @Before
    public void before() {
        AmazonS3 s3 = AmazonS3Client.builder()
                                    .withCredentials(new ProfileCredentialsProvider())
                                    .withRegion(awsConfig.getString("s3.region"))
                                    .build();
        blobStore = new S3BlobStoreService(s3, awsConfig);
    }

    @Test
    public void testBlobHappyPath() throws FileNotFoundException {
        String fileName = Thread.currentThread().getContextClassLoader().getResource(UPLOAD_FILE_NAME).getPath();
        FileInputStream inputStream = new FileInputStream(fileName);
        BlobData data = new BlobData(inputStream);

        BlobLocation location = blobStore.putObject(data);
        Assert.assertNotNull(location);

        String url = blobStore.getObjectUrl(location);
        Assert.assertNotNull(url);

        // should not throw an exception when this happens.
        blobStore.deleteObject(location);
    }

    @Test(expected = BlobStoreException.class)
    public void testCreateNullObject() {
        blobStore.putObject(null);
    }

    @Test(expected = BlobStoreException.class)
    public void testDeleteNullObject() {
        blobStore.deleteObject(null);
    }

}
