package com.bizbeam.s3.uploader.jcommander;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class JCommanderParams {

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = { "-i", "-int", "-interval" }, description = "Interval between calls in seconds")
    private Integer intervalSeconds = -1;

    @Parameter(names = { "-k", "-key", "-accessKey" }, description = "AWSAccessKeyId to access Amazon S3 cloud storage")
    private String accessKey;

    @Parameter(names = { "-s", "-secret", "-secretKey" }, description = "AWSAccessKeyId to access Amazon S3 cloud storage")
    private String secretKey;

    @Parameter(names = { "-b", "-bucket", "-bucketName" }, description = "AWSAccessKeyId to access Amazon S3 cloud storage")
    private String bucketName;

    public List<String> getParameters() {
        return parameters;
    }

    public Integer getIntervalSeconds() {
        return intervalSeconds;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public void setIntervalSeconds(Integer intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
