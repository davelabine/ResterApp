package com.davelabine.resterapp.integration.platform;

import com.davelabine.resterapp.platform.blob.S3BlobStoreService;
import com.davelabine.resterapp.platform.api.*;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by dave on 12/10/16.
 */

public class BlobStoreServiceTest {
    S3BlobStoreService blobStore;

    @Before
    public void before() {
        AmazonS3Client s3 = new AmazonS3Client(new ProfileCredentialsProvider());
        blobStore = new S3BlobStoreService(s3);
    }

    @Test
    public void testPutObject() {
        BlobData data = null;
        blobStore.putObject(data);
    }

    @Test
    public void testGetObject() {
        BlobLocation location = null;
        blobStore.getObject(location);
    }
}
