package com.boardserver.service;

import com.boardserver.config.AWSConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Service
@RequiredArgsConstructor
public class SnsService {

    private final AWSConfig awsConfig;

    public SnsClient getSnsClient() {
        return SnsClient.builder()
                .credentialsProvider(getAwsCredentials(awsConfig.getAwsAccessKey(), awsConfig.getAwsSecretKey()))
                .region(Region.of(awsConfig.getAwsRegion()))
                .build();
    }

    private AwsCredentialsProvider getAwsCredentials(String accessKeyId, String secretAccessKey) {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        return () -> awsBasicCredentials;
    }

}
