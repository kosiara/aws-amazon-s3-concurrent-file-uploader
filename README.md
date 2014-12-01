aws-amazon-s3-concurrent-file-uploader
===========================

concurrent uploads to aws amazon s3 cloud storage

What it does?
==============
Aws-amazon-s3-concurrent-file-uploader opens many concurrent connections to Amazon AWS S3 cloud storage. It uploads all files that reside in the application directory and deletes them from S3.
Each concurrent connection creates a separate, random folder on S3 so there is no problem with overriding data. With this simple tool you can stress test your connection to Amazon simulating
thousands of clients as well as heavy use by copying large files into the program directory.

Example use:
=============
6 files copied to program directory, -t (threads) set to 1000
Number of connections: 1000 (to check bucket availability) + 6 * 2 * 1000 = 13000
Concurrent connections: 1000+ (1000 - 12000 opened in the first 3 seconds of operation)


How to run:
===========

java -jar app.jar

java -jar app.jar -t 5

java -jar app.jar -t 5 -k youAmAzONAWSKey -s 2mmsdSE34fCYourAwsAmazonSecret -b yourBucketName

Parameters
==========

* -t/-threads/-threadsNumber  - number of concurrent threads to be opened
* -k/-key/-accessKey  -  AWSAccessKeyId to access Amazon S3 cloud storage that you can obtain from https://console.aws.amazon.com/iam/home
* -s/-secret/-secretKey - AWSSecretKey used as a password
* -b/-bucket/-bucketName - bucket name
