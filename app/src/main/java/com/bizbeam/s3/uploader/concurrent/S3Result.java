package com.bizbeam.s3.uploader.concurrent;

public class S3Result {

    private Thread thread;

    private boolean result = false;

    //in millis
    private long sendTime = 0;


    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }
}
