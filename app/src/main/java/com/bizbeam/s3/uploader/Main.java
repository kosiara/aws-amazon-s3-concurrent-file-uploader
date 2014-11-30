package com.bizbeam.s3.uploader;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.internal.Lists;
import com.bizbeam.s3.uploader.concurrent.S3ConnectorThread;
import com.bizbeam.s3.uploader.concurrent.S3Result;
import com.bizbeam.s3.uploader.jcommander.JCommanderParams;
import org.apache.log4j.Logger;
import java.io.File;
import java.util.List;

public class Main {

    private static String AWSAccessKeyId= "SAMPLESAMPLESAMPLE";
    private static String AWSSecretKey="2SamPleSample3456SaMpleSample3578SampleSaMpLe";
    private static String AWSBucketName = "sampleBucketName";

	public static void main (String args[]) {

        long correctConnections = 0;
        long failedConnections = 0;
        JCommanderParams jcp = new JCommanderParams();

        try {
            LOGGER.info("Initializing connector...");

            new JCommander(jcp, args);
            processArguments(jcp);

            LOGGER.info("interval : " + jcp.getIntervalSeconds() + " [sec]");
            LOGGER.info("access key : " + jcp.getAccessKey());
            LOGGER.info("secret key : " + jcp.getSecretKey());
            LOGGER.info("bucket name : " + jcp.getBucketName());
            LOGGER.info("current dir : " + new File(".").getAbsolutePath());
            LOGGER.info("number of threads : " + jcp.getNumberOfThreads());

            List<S3Result> s3ResultThreads = Lists.newArrayList();
            for (int i = 0; i < jcp.getNumberOfThreads(); i++)
                s3ResultThreads.add(new S3Result());

            int i = 0;
            for (S3Result s3Result : s3ResultThreads) {
                s3Result.setThread(new S3ConnectorThread(jcp, i++));
                s3Result.getThread().run();
            }

            for (S3Result s3Result : s3ResultThreads)
                s3Result.getThread().join();




                LOGGER.info("");
                LOGGER.info("Current results");
                LOGGER.info("==========================");
                LOGGER.info("Correct connections:" + correctConnections);
                LOGGER.info("Failed connections:" + failedConnections + "\r\n\r\n");


        } catch (Exception exc) {
            LOGGER.error(exc);
            System.out.println("\r\n" + exc.toString());
        }
    }

    private static void processArguments(JCommanderParams jcp) {
        int paramNum = jcp.getParameters().size();

        if (jcp.getIntervalSeconds() ==  -1)
            jcp.setIntervalSeconds(5);

        if (jcp.getAccessKey() ==  null)
            jcp.setAccessKey(AWSAccessKeyId);

        if (jcp.getSecretKey() ==  null)
            jcp.setSecretKey(AWSSecretKey);

        if (jcp.getBucketName() ==  null)
            jcp.setBucketName(AWSBucketName);
    }

    private static void sleep(int millis) {
        try { Thread.sleep(millis); }  catch (Exception exc) { }
    }

    private static final Logger LOGGER = Logger.getLogger(Main.class);
}
