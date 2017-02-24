package com.davelabine.resterapp.platform.blob;

import java.io.IOException;
import java.io.File;
import java.net.URL;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;

import com.davelabine.resterapp.platform.api.BlobData;
import com.davelabine.resterapp.platform.api.BlobLocation;
import com.davelabine.resterapp.platform.api.BlobStoreService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dave on 12/9/16.
 */
public class S3BlobStoreService implements BlobStoreService {
    private static String bucketName = "akiajzepuyoh2e3z73jqcomhaystacksoftwarearq";
    private static String getKeyName = "bash-hints.txt";
    private static String putKeyName = "cuteimage.jpg";

    private static final Logger logger = LoggerFactory.getLogger(S3BlobStoreService.class);

    //private final S3Configuration s3Config;
    private final AmazonS3Client s3;

    @Inject
    public S3BlobStoreService(final AmazonS3Client s3Client) {
        this.s3 = s3Client;
        //this.s3Config = s3Config;
    }

    @Override
    public BlobLocation putObject(BlobData data) {
        logger.info("S3BlobStoreService.putObject");
        BlobLocation blobLocation = BlobLocation.builder(bucketName, generateUniqueKey()).build();
        File file = new File(data.getFileName());
        try {
            s3.putObject(
                    new PutObjectRequest(bucketName, blobLocation.getKey(), file)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonServiceException ase) {
            logger.error("Request made it to Amazon S3, but was rejected with an error response. Error: {}, " +
                            "HTTP Status Code: {}, AWS Error Code: {}, Error Type: {}, Request ID: {}",
                    ase.getMessage(), ase.getStatusCode(), ase.getErrorCode(), ase.getErrorType(), ase.getRequestId());
            return null;
        } catch (AmazonClientException ace) {
            logger.error("AmazonClient internal error: {}", ace.getMessage());
            return null;
        }

        return blobLocation;
    }

    @Override
    public BlobData getObject(BlobLocation key) {
        /*
        logger.info("BlobStoreService.getObject");

        try {
            S3Object metadata = s3.getObject(new GetObjectRequest(bucketName, getKeyName));
            //ObjectMetadata metadata = s3.getObjectMetadata(key.getBucketName(), key.getKey());
            //s3.getResourceURL()
            logger.info("Metadata: {}", metadata.toString());
        } catch (AmazonServiceException ase) {
            logger.error("Request made it to Amazon S3, but was rejected with an error response. Error: {}, " +
                            "HTTP Status Code: {}, AWS Error Code: {}, Error Type: {}, Request ID: {}",
                    ase.getMessage(), ase.getStatusCode(), ase.getErrorCode(), ase.getErrorType(), ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.error("AmazonClient internal error: {}", ace.getMessage());

        }

        logger.info("Done!");
        */
        return null;
    }

    @Override
    public String getObjectUrl(BlobLocation key) {
        logger.info("BlobStoreService.getObjectURL");
        return s3.getResourceUrl(bucketName, key.getKey());
    }

    @Override
    public boolean deleteObject(BlobLocation key) {
        return false;
    }

    private String generateUniqueKey() {
        return UUID.randomUUID().toString();
    }
}

