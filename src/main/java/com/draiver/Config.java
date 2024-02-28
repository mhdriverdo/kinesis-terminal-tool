package com.draiver;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public AWSCredentialsProvider credentials() {
        return new DefaultAWSCredentialsProviderChain();
    }

    @Bean
    public AmazonKinesis buildAmazonKinesis() {
        return AmazonKinesisClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(credentials())
                .build();
    }
}
