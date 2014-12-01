package com.bizbeam.s3.uploader.concurrent;

import com.beust.jcommander.internal.Lists;
import com.bizbeam.s3.uploader.S3Connector;
import com.bizbeam.s3.uploader.jcommander.JCommanderParams;
import com.google.common.base.Stopwatch;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class S3ConnectorThread extends Thread {


    private int mThreadNo = -1;
    S3Connector s3Connector;
    JCommanderParams mJCommanderParams;
    private boolean result = false;
    Stopwatch stopwatch = Stopwatch.createUnstarted();
    String mTemporaryFolder;
    double mAvgTime;

    public S3ConnectorThread(JCommanderParams jCommanderParams, int threadNo) {
        super();
        mThreadNo = threadNo;
        mJCommanderParams = jCommanderParams;
        mTemporaryFolder = UUID.randomUUID().toString().replace("-","");
    }

    @Override
    public void run() {
        super.run();

        try {
            s3Connector = new S3Connector(mJCommanderParams.getBucketName(),
                    mJCommanderParams.getAccessKey(), mJCommanderParams.getSecretKey());
            s3Connector.initialize();

            sleep(2000);

            sendFiles();
            result = true;

        } catch (Exception exc) {
            LOGGER.error("Error in thread no. " + mThreadNo, exc);
        }

    }

    private void sendFiles() {
        try {

            String[] allFiles = new File(".").list();
            List<File> filesToUpload = Lists.newArrayList();
            for (String file : allFiles) {
                if (file.contains("app") || file.contains(".jar") || file.contains(".sh"))
                    continue;
                File f = new File(file);
                if (f.isDirectory())
                    continue;

                filesToUpload.add(f);
            }

            stopwatch.start();

            for (File file : filesToUpload) {
                s3Connector.uploadFile(mTemporaryFolder, file);
                s3Connector.removeFile(mTemporaryFolder, file.getName());
            }

            stopwatch.stop();
            long milliSeconds = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            double secondsForSingleOperation = (milliSeconds/(filesToUpload.size()*2.0))/1000;
            mAvgTime = secondsForSingleOperation;

        } catch (Exception exc) {
            LOGGER.error("Error in connection in thread no. : " + mThreadNo, exc);
        }
    }

    public boolean getResult() {
        return result;
    }

    public double getAvgTime() {
        return mAvgTime;
    }

    private static void sleep(int millis) {
        try { Thread.sleep(millis); }  catch (Exception exc) { }
    }

    private static final Logger LOGGER = Logger.getLogger(S3ConnectorThread.class);
}
