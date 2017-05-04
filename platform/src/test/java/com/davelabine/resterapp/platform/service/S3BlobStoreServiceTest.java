package com.davelabine.resterapp.platform.service;

import com.davelabine.resterapp.platform.api.exceptions.BlobStoreException;
import com.davelabine.resterapp.platform.api.model.BlobData;
import com.davelabine.resterapp.platform.api.model.BlobLocation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.typesafe.config.Config;

import java.io.File;
import java.io.FileInputStream;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


/**
 * Created by dave on 2/23/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class S3BlobStoreServiceTest {
    public static final String FAKE_URL = "http://fakety.fake";
    public static final String FAKE_BUCKET = "FAKE_BUCKET";
    public static final String FAKE_KEY = "FAKE_KEY";

    @InjectMocks
    private S3BlobStoreService underTest;

    @Mock
    private AmazonS3Client mockS3;

    @Mock
    private Config mockConfig;

    @Mock
    private FileInputStream inputStream;

    @Before
    public void before() {
        when(mockConfig.getString("s3.bucket")).thenReturn(FAKE_BUCKET);
    }

    // Test that a blob location is returned when an object is put
    @Test
    public void testPutObject() {
        BlobData data = new BlobData(inputStream);
        BlobLocation returned = underTest.putObject(data);
        assertNotNull(returned);
        assertNotNull(returned.getBucketName());
        assertNotNull(returned.getKey());
    }

    // Test that PutObject can handle exceptions appropriately
    @Test(expected=BlobStoreException.class)
    public void testPutObjectAseExceptionsCaught() {
        reset(mockS3);
        BlobData data = new BlobData(inputStream);
        when(mockS3.putObject(any(PutObjectRequest.class))).thenThrow(new AmazonServiceException("Fake Exception!"));
        underTest.putObject(data); // Should throw an exception
    }


    @Test(expected=BlobStoreException.class)
    public void testPutObjectACExceptionsCaught() {
        reset(mockS3);
        BlobData data = new BlobData(inputStream);
        when(mockS3.putObject(any(PutObjectRequest.class))).thenThrow(new AmazonClientException("Fake Exception!"));
        assertNull(underTest.putObject(data));
    }

    @Test
    public void testGetUrl() {
        BlobLocation location = new BlobLocation(FAKE_BUCKET, FAKE_KEY);
        assertNotNull(underTest.getObjectUrl(location));
    }

    // Test deleting objects throws exceptions
    @Test(expected=BlobStoreException.class)
    public void testDeleteObjectAseExceptionsCaught() {
        BlobLocation location = new BlobLocation(FAKE_BUCKET, FAKE_KEY);
        Mockito.doThrow(new AmazonServiceException("Fake Exception!")).when(mockS3).deleteObject(anyString(), anyString());
        underTest.deleteObject(location);
    }

    @Test(expected=BlobStoreException.class)
    public void testDeleteObjectACEExceptionsCaught() {
        BlobLocation location = new BlobLocation(FAKE_BUCKET, FAKE_KEY);
        Mockito.doThrow(new AmazonClientException("Fake Exception!")).when(mockS3).deleteObject(anyString(), anyString());
        underTest.deleteObject(location);
    }

}
