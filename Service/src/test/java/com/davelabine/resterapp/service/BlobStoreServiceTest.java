package com.davelabine.resterapp.service;

import com.amazonaws.services.s3.AmazonS3Client;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by dave on 12/10/16.
 */

public class BlobStoreServiceTest {
    BlobStoreService blobStore;

    @Before
    public void before() {
        // TODO: Figure out how to seperate into appropriate integration and unit tests
        AmazonS3Client s3 = new AmazonS3Client();
        blobStore = new BlobStoreService(s3);
    }

    @Test
    public void testPutObject() {
        try {
            blobStore.putObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
