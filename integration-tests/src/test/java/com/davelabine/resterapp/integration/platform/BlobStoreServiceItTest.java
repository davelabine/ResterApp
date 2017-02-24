package com.davelabine.resterapp.integration.platform;

import com.davelabine.resterapp.platform.blob.*;
import com.davelabine.resterapp.platform.api.*;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by dave on 12/10/16.
 */

public class BlobStoreServiceItTest {
    private static String UPLOAD_FILE_NAME = "resources/images/DSC_0133.jpg";
    BlobStoreService blobStore;

    @Before
    public void before() {
        AmazonS3Client s3 = new AmazonS3Client(new ProfileCredentialsProvider());
        blobStore = new S3BlobStoreService(s3);
    }

    @Test
    public void testPutObjectAndGetUrl() {
        BlobData data = new BlobData(UPLOAD_FILE_NAME);
        BlobLocation location = blobStore.putObject(data);
        Assert.assertNotNull(location);
        
        String url = blobStore.getObjectUrl(location);
        Assert.assertNotNull(url);
    }
}
