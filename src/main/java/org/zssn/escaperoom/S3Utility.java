package org.zssn.escaperoom;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class S3Utility {
    private static final Region REGION = Region.US_EAST_1; // La regione del mio bucket 
    private static final S3Client s3 = S3Client.builder()
            .region(REGION)
            .credentialsProvider(ProfileCredentialsProvider.create())
            .build();

    public static S3Client getS3Client() {
        return s3;
    }
}