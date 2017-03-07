package com.davelabine.resterapp.platform.service;

import java.io.File;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;

import com.google.inject.name.Named;
import com.typesafe.config.Config;
import com.davelabine.resterapp.platform.api.service.BlobStoreService;
import com.davelabine.resterapp.platform.api.model.BlobData;
import com.davelabine.resterapp.platform.api.model.BlobLocation;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dave on 12/9/16.
 */
public class S3BlobStoreService implements BlobStoreService {
    private static final Logger logger = LoggerFactory.getLogger(S3BlobStoreService.class);

    private final Config awsConfig;
    private final AmazonS3Client s3;

    @Inject
    public S3BlobStoreService(final AmazonS3Client s3Client, @Named("aws.conf") final Config awsConfig) {
        this.s3 = s3Client;
        this.awsConfig = awsConfig;
        s3.setRegion(Region.fromValue(awsConfig.getString("s3.region")).toAWSRegion());
    }

    @Override
    public BlobLocation putObject(BlobData data) {
        logger.info("data: {}", data);
        BlobLocation blobLocation = BlobLocation.builder(awsConfig.getString("s3.bucket"),
                                                            generateUniqueKey(data)).build();
        File file = new File(data.getFileName());
        try {
            s3.putObject(
                    new PutObjectRequest(awsConfig.getString("s3.bucket"), blobLocation.getKey(), file)
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
        logger.info("object saved! {}", blobLocation);
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
        logger.info("key: {}", key);
        return "https://s3-" + awsConfig.getString("s3.region") + ".amazonaws.com/"
                + key.getBucketName() + "/" + key.getKey();
    }

    @Override
    public boolean deleteObject(BlobLocation key) {
        return false;
    }

    private String generateUniqueKey(BlobData data) {
        String fileName = UUID.randomUUID().toString();

        int i = data.getFileName().lastIndexOf('.');
        if (i > 0) {
            fileName += data.getFileName().substring(i);
        }
        return  fileName;
    }
}

