package com.draiver;

import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.model.*;
import com.amazonaws.services.kinesis.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.kinesis.model.KinesisException;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

@Service
public class KinesisService {

    @Autowired
    private AmazonKinesis kinesisClient;

    public void sendEvent(String message, String streamName) {
        var output = new PutRecordRequest();
        output.setStreamName(streamName);
        output.setPartitionKey("partitionKey");
        output.withData(ByteBuffer.wrap(message.getBytes()));

        PutRecordResult response = kinesisClient.putRecord(output);
        System.out.println("Message send to topic '" + streamName + "' sequence ID : " + response.getSequenceNumber());
    }

    public void listenToKinesisStream(String streamName) {
        try {
            String shardIterator;
            String lastShardId = null;

            DescribeStreamRequest describeStreamRequest = new DescribeStreamRequest().withStreamName(streamName);

            List<Shard> shards = kinesisClient.describeStream(describeStreamRequest).getStreamDescription().getShards();

            for (Shard shard : shards) {
                lastShardId = shard.getShardId();
            }
            GetShardIteratorRequest getShardIteratorRequest = new GetShardIteratorRequest()
                    .withStreamName(streamName)
                    .withShardId(lastShardId)
                    .withShardIteratorType(ShardIteratorType.TRIM_HORIZON);
            shardIterator = kinesisClient.getShardIterator(getShardIteratorRequest).getShardIterator();

            List<Record> records;
            while (true) {
                GetRecordsRequest getRecordsRequest = new GetRecordsRequest()
                        .withShardIterator(shardIterator);
                GetRecordsResult result = kinesisClient.getRecords(getRecordsRequest);

                records = result.getRecords();
                for (Record record : records) {
                    System.out.println("Record: " + new String(record.getData().array()));
                }

                shardIterator = result.getNextShardIterator();
            }
        } catch (KinesisException e) {
            System.out.println("Error while sending message: " + e.getMessage());
            System.out.println("Stack:" + Arrays.toString(e.getStackTrace()));
        }
    }

}

