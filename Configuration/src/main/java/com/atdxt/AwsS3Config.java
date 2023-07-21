package com.atdxt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsS3Config {

    @Bean
    public S3Client s3Client() {
        Region region = Region.US_EAST_1;
        return S3Client.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(region)
                .build();
    }
}
