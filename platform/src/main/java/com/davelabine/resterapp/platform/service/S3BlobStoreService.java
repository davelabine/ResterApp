package com.davelabine.resterapp.platform.service;

import java.io.File;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.xspec.B;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;

import com.davelabine.resterapp.platform.api.exceptions.BlobStoreException;
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
    private final AmazonS3 s3;

    @Inject
    public S3BlobStoreService(final AmazonS3 s3Client, @Named("aws.conf") final Config awsConfig) {
        this.s3 = s3Client;
        this.awsConfig = awsConfig;
    }

    @Override
    public BlobLocation putObject(BlobData data) {
        logger.info("data: {}", data);
        BlobLocation blobLocation = new BlobLocation(
                                        awsConfig.getString("s3.bucket"),
                                        generateUniqueKey(data));
        try {
            //ObjectMetadata metadata = new ObjectMetadata()
            PutObjectRequest putReq = new PutObjectRequest(awsConfig.getString("s3.bucket"),
                                                        blobLocation.getKey(),
                                                        data.getInputStream(),
                                                        null);
            s3.putObject(putReq.withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (AmazonServiceException ase) {
            throw new BlobStoreException("Request made it to Amazon S3, but was rejected with an error response. Error: " +
                    ase.getMessage() + ", HTTP Status Code: " + ase.getStatusCode() + ", AWS Error Code: " + ase.getErrorCode()
                    + "Error Type: " + ase.getErrorType() + ", Request ID: {}" + ase.getRequestId(), ase);
        } catch (AmazonClientException ace) {
            throw new BlobStoreException("AmazonClient internal error: " + ace.getLocalizedMessage(), ace);
        }
        logger.info("object saved! {}", blobLocation);
        return blobLocation;
    }


    @Override
    public BlobData getObject(BlobLocation key) {

        logger.info("BlobStoreService.getObject");

        /* This will get us a stream to download a blob URL, we don't need this quite yet.
        try {
            S3Object metadata = s3.getObject(new GetObjectRequest(awsConfig.getString("s3.bucket"), key.getKey()));
            //ObjectMetadata metadata = s3.getObjectMetadata(key.getBucketName(), key.getKey());
            //s3.getResourceURL()


        } catch (AmazonServiceException ase) {
            throw new BlobStoreException("Request made it to Amazon S3, but was rejected with an error response. Error: " +
                    ase.getMessage() + ", HTTP Status Code: " + ase.getStatusCode() + ", AWS Error Code: " + ase.getErrorCode()
                    + "Error Type: " + ase.getErrorType() + ", Request ID: {}" + ase.getRequestId(), ase);
        } catch (AmazonClientException ace) {
            throw new BlobStoreException("AmazonClient internal error: " + ace.getLocalizedMessage(), ace);
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
    public void deleteObject(BlobLocation blobLocation) {
        logger.info("BlobStoreService.deleteObject {}", blobLocation);
        try {
            s3.deleteObject(blobLocation.getBucketName(), blobLocation.getKey());
        } catch (AmazonServiceException ase) {
            throw new BlobStoreException("Request made it to Amazon S3, but was rejected with an error response. Error: " +
                    ase.getMessage() + ", HTTP Status Code: " + ase.getStatusCode() + ", AWS Error Code: " + ase.getErrorCode()
                    + "Error Type: " + ase.getErrorType() + ", Request ID: {}" + ase.getRequestId(), ase);
        } catch (AmazonClientException ace) {
            throw new BlobStoreException("AmazonClient internal error: " + ace.getLocalizedMessage(), ace);
        }
        logger.info("object deleted!");

    }

    private String generateUniqueKey(BlobData data) {
        return UUID.randomUUID().toString();
    }
}

