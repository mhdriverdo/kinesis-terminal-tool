package com.draiver;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
@Service
public class KinesisService {

    private static final Logger logger = LoggerFactory.getLogger(Commands.class);

    @Autowired
    private AmazonKinesis kinesisClient;

    public void sendEvent(String message, String streamName) {
        var output = new PutRecordRequest();
        output.setStreamName(streamName);
        output.setPartitionKey("partitionKey");
        output.withData(ByteBuffer.wrap(message.getBytes()));

        PutRecordResult response = kinesisClient.putRecord(output);
        logger.info("Message send to topic '" + streamName + "' sequence ID : " + response.getSequenceNumber());
    }
}

