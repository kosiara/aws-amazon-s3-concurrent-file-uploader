package com.bizbeam.s3.uploader;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.log4j.Logger;

import java.io.File;

public class S3Connector {

    private String mAWS_S3_BUCKET;
    private String mAWS_ACCESS_KEY;
    private String mAWS_SECRET_KEY;
    private AmazonS3 mAmazonS3;
    private boolean mInitialized = false;

    public S3Connector(String s3Bucket, String s3AccessKey, String s3SecretKey) {
        mAWS_S3_BUCKET = s3Bucket;
        mAWS_ACCESS_KEY = s3AccessKey;
        mAWS_SECRET_KEY = s3SecretKey;
    }

    public void initialize()  throws Exception {
        if ((mAWS_ACCESS_KEY != null) && (mAWS_SECRET_KEY != null)) {
            AWSCredentials awsCredentials = new BasicAWSCredentials(mAWS_ACCESS_KEY, mAWS_SECRET_KEY);
            mAmazonS3 = new AmazonS3Client(awsCredentials);

            try {
                mAmazonS3.listBuckets();
            } catch (AmazonS3Exception exc) {
                if (exc.getErrorCode().equals("SignatureDoesNotMatch") || exc.getErrorCode().equals("InvalidAccessKeyId"))
                    throw new Exception("WRONG AWS KEY OR PASSWORD!");
            }

            LOGGER.info("Using S3 Bucket: " + mAWS_S3_BUCKET);
            mInitialized = true;
        }
    }

    public void uploadFile(File file) throws Exception {
        if (!mInitialized)
            throw new Exception(CONNECTOR_NOT_INITIAZLIED_ERROR_MSG);

        PutObjectRequest putObjectRequest = new PutObjectRequest(mAWS_S3_BUCKET, file.getName(), file);
        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        mAmazonS3.putObject(putObjectRequest);
    }

    public void createNewBucket(String bucketName) throws Exception {
        if (!mInitialized)
            throw new Exception(CONNECTOR_NOT_INITIAZLIED_ERROR_MSG);

        mAmazonS3.createBucket(bucketName);
    }

    public void removeFile(String objectKey) throws Exception {
        if (!mInitialized)
            throw new Exception(CONNECTOR_NOT_INITIAZLIED_ERROR_MSG);

        mAmazonS3.deleteObject(mAWS_S3_BUCKET, objectKey);
    }


    private static String CONNECTOR_NOT_INITIAZLIED_ERROR_MSG = "Connector not initialized";
    private static final Logger LOGGER = Logger.getLogger(S3Connector.class);
}
