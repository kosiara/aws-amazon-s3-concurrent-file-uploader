package com.bizbeam.s3.uploader;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.internal.Lists;
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

            S3Connector s3Connector = new S3Connector(jcp.getBucketName(), jcp.getAccessKey(), jcp.getSecretKey());
            s3Connector.initialize();

            while (true) {

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

                    correctConnections++;
                } catch (Exception exc) {
                    LOGGER.error("Error in connection: ", exc);
                    failedConnections++;
                }

                LOGGER.info("");
                LOGGER.info("Current results");
                LOGGER.info("==========================");
                LOGGER.info("Correct connections:" + correctConnections);
                LOGGER.info("Failed connections:" + failedConnections + "\r\n\r\n");

                LOGGER.info("Sleeping " + jcp.getIntervalSeconds() + " seconds...");
                sleep(jcp.getIntervalSeconds() * 1000);
            }

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
