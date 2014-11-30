package com.bizbeam.s3.uploader.concurrent;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.internal.Lists;
import com.bizbeam.s3.uploader.S3Connector;
import com.bizbeam.s3.uploader.jcommander.JCommanderParams;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

public class S3ConnectorThread extends Thread {


    private int mThreadNo = -1;
    S3Connector s3Connector;
    JCommanderParams mJCommanderParams;
    private boolean result = false;

    public S3ConnectorThread(JCommanderParams jCommanderParams, int threadNo) {
        super();
        mThreadNo = threadNo;
        mJCommanderParams = jCommanderParams;
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


            for (File file : filesToUpload) {
                s3Connector.uploadFile(file);
                s3Connector.removeFile(file.getName());
            }

        } catch (Exception exc) {
            LOGGER.error("Error in connection in thread no. : " + mThreadNo, exc);
        }
    }

    public boolean getResult() {
        return result;
    }

    private static void sleep(int millis) {
        try { Thread.sleep(millis); }  catch (Exception exc) { }
    }

    private static final Logger LOGGER = Logger.getLogger(S3ConnectorThread.class);
}