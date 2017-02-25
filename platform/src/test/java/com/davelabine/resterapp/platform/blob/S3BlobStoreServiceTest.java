package com.davelabine.resterapp.platform.blob;

import com.davelabine.resterapp.platform.api.BlobData;
import com.davelabine.resterapp.platform.api.BlobLocation;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.typesafe.config.Config;

import static org.mockito.Mockito.*;



/**
 * Created by dave on 2/23/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class S3BlobStoreServiceTest {
    public static final String FAKE_FILENAME = "FAKE_FILE";
    public static final String FAKE_URL = "http://fakety.fake";
    public static final String FAKE_BUCKET = "FAKE_BUCKET";
    public static final String FAKE_KEY = "FAKE_KEY";

    @InjectMocks
    private S3BlobStoreService underTest;

    @Mock
    private AmazonS3Client mockS3;

    @Mock
    private Config mockConfig;

    @Before
    public void before() {
        when(mockConfig.getString("s3.bucket")).thenReturn(FAKE_BUCKET);
    }

    // Test that a blob location is returned when an object is put
    @Test
    public void testPutObject() {
        BlobData data = new BlobData(FAKE_FILENAME);

        BlobLocation returned = underTest.putObject(data);
        Assert.assertNotNull(returned);
        Assert.assertNotNull(returned.getBucketName());
        Assert.assertNotNull(returned.getKey());
    }

    // Test that PutObject can handle exceptions appropriately
    @Test
    public void testPutObjectExceptions() {
        BlobData data = new BlobData(FAKE_FILENAME);

        when(mockS3.putObject(any(PutObjectRequest.class))).thenThrow(new AmazonServiceException("Fake Exception!"));
        Assert.assertNull(underTest.putObject(data));

        reset(mockS3);
        when(mockS3.putObject(any(PutObjectRequest.class))).thenThrow(new AmazonClientException("Fake Exception!"));
        Assert.assertNull(underTest.putObject(data));
    }

    @Test
    public void testGetUrl() {
        BlobLocation location = BlobLocation.builder(FAKE_BUCKET, FAKE_KEY).build();
        when(mockS3.getResourceUrl(anyString(), anyString())).thenReturn(new String(FAKE_URL));
        Assert.assertNotNull(underTest.getObjectUrl(location));
    }
}
