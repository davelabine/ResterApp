package com.davelabine.resterapp.platform.blob;

import java.io.IOException;
import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
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
    private static String uploadFileName = "src/main/webapp/images/DSC_0133.jpg";

    private static final Logger logger = LoggerFactory.getLogger(S3BlobStoreService.class);

    //private final S3Configuration s3Config;
    private final AmazonS3 s3;

    @Inject
    public S3BlobStoreService(final AmazonS3Client s3Client) {
        this.s3 = s3Client;
        //this.s3Config = s3Config;
    }

    @Override
    BlobLocation putObject(BlobData data) throws IOException {
        logger.info("S3BlobStoreService.putObject");
        File file = new File(data.getFilename());
        try {
            s3.putObject(bucketName, putKeyName, file);
        } catch (AmazonServiceException ase) {
            logger.error("Request made it to Amazon S3, but was rejected with an error response. Error: {}, " +
                            "HTTP Status Code: {}, AWS Error Code: {}, Error Type: {}, Request ID: {}",
                    ase.getMessage(), ase.getStatusCode(), ase.getErrorCode(), ase.getErrorType(), ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.error("AmazonClient internal error: {}", ace.getMessage());
        }
    }

    /*
    public static void main(String[] args) throws IOException {
        AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
        try {
            System.out.println("Uploading a new object to S3 from a file\n");
            File file = new File(uploadFileName);
            s3client.putObject(new PutObjectRequest(
                    bucketName, keyName, file));

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
    */
    @Override
    public  BlobData getObject(BlobLocation key) throws IOException {
        logger.info("BlobStoreService.getObject");

        try {
            //S3Object object = s3.getObject(new GetObjectRequest(bucketName, keyName));
            ObjectMetadata metadata = s3.getObjectMetadata(key.getBucketName(), key.getKey());
            logger.info("Metadata: {}", metadata.toString());
        } catch (AmazonServiceException ase) {
            logger.error("Request made it to Amazon S3, but was rejected with an error response. Error: {}, " +
                            "HTTP Status Code: {}, AWS Error Code: {}, Error Type: {}, Request ID: {}",
                    ase.getMessage(), ase.getStatusCode(), ase.getErrorCode(), ase.getErrorType(), ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.error("AmazonClient internal error: {}", ace.getMessage());

        }

        logger.info("Done!");
    }
}
