package com.bizbeam.s3.uploader.concurrent;

public class S3Result {

    private S3ConnectorThread thread;

    private boolean result = false;

    //in seconds
    private double sendAvgTime = 0;

    public S3ConnectorThread getThread() {
        return thread;
    }

    public void setThread(S3ConnectorThread thread) {
        this.thread = thread;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public double getSendAvgTime() {
        return sendAvgTime;
    }

    public void setSendAvgTime(double sendAvgTime) {
        this.sendAvgTime = sendAvgTime;
    }
}
