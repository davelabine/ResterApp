package com.davelabine.resterapp.integration.platform;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
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
    private static final Config secretConfig = ConfigFactory.load("secret.conf");

    BlobStoreService blobStore;

    @Before
    public void before() {
        BasicAWSCredentials creds = new BasicAWSCredentials(
                secretConfig.getString("Secret.AWS_ACCESS_KEY_ID"),
                secretConfig.getString("Secret.AWS_SECRET_ACCESS_KEY"));
        AWSStaticCredentialsProvider credProvider = new AWSStaticCredentialsProvider(creds);

        AmazonS3 s3 = AmazonS3Client.builder()
                                    .withCredentials(credProvider)
                                    .withRegion(awsConfig.getString("s3.region"))
                                    .build();
        blobStore = new S3BlobStoreService(s3, awsConfig);
    }

    @Test
    public void testBlobHappyPath() throws FileNotFoundException {
        String fileName = Thread.currentThread().getContextClassLoader().getResource(UPLOAD_FILE_NAME).getPath();
        FileInputStream inputStream = new FileInputStream(fileName);
        long length = new File(fileName).length();
        BlobData data = new BlobData(inputStream, length);

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
