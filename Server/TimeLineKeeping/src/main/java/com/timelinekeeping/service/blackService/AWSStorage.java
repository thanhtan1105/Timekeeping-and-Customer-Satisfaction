package com.timelinekeeping.service.blackService;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.util.CryptoUtils;

import java.io.File;

/**
 * Created by lethanhtan on 10/31/16.
 */
public class AWSStorage {

    public static void main(String args[]) {
        File file = new File("D:\\CP\\FILE\\customerCode4.jpg");
        if (file != null) {
            System.out.println("Link ne:" + uploadFile(file, "customerCode4.jpg"));
        }

    }

    public static String uploadFile(File file, String fileName) {
        AWSCredentials credentials = null;
        try {
            String accessKey = CryptoUtils.doDecrypt(AppConfigKeys.getInstance().getAmazonPropertyValue("amazon.s3.accesskey"));
            String passwordKey = CryptoUtils.doDecrypt(AppConfigKeys.getInstance().getAmazonPropertyValue("amazon.s3.passwordkey"));
            credentials = new BasicAWSCredentials(accessKey, passwordKey);
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }

        AmazonS3 s3 = new AmazonS3Client(credentials);
        String bucketName = "customersatisfaction";

        System.out.println("===========================================");
        System.out.println("Getting Started with Amazon S3");
        System.out.println("===========================================\n");
        try {
            s3.putObject(new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
            return AppConfigKeys.getInstance().getAmazonPropertyValue("amazon.s3.link") + fileName;
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }

        return null;
    }
}
