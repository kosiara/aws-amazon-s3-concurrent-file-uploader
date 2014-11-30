aws-amazon-s3-file-uploader
===========================

concurrent uploads to aws amazon s3 cloud storage

How to run:

java -jar app.jar

java -jar app.jar -i 5

java -jar app.jar -i 5 -k youAmAzONAWSKey -s 2mmsdSE34fCYourAwsAmazonSecret -b yourBucketName

Parameters
==========

* -i/-int/-interval  -  interval in seconds between calls
* -k/-key/-accessKey  -  AWSAccessKeyId to access Amazon S3 cloud storage that you can obtain from https://console.aws.amazon.com/iam/home
* -s/-secret/-secretKey - AWSSecretKey used as a password
* -b/-bucket/-bucketName - bucket name
