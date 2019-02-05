package com.example.challenge.moodTracker.integration.sqs.producer;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.example.challenge.moodTracker.integration.MessageProducer;

public class SQSMessageProducer implements MessageProducer {

    private String QUEUE_NAME;
    private String QUEUE_URL;

    AmazonSQS amazonSQS;

    @Override
    public void sendMessage(Object message) throws Exception {
        if(message instanceof  SendMessageRequest){
            amazonSQS.sendMessage((SendMessageRequest) message);
        }
        else {
            throw new Exception("Wrong message type given");
        }
    }

    @Override
    public String getQueueName() {
        return QUEUE_NAME;
    }

    @Override
    public String getQueueUrl() {
        return QUEUE_URL;
    }

    private SQSMessageProducer(CreateQueueRequest createQueueRequest, AmazonSQS sqs){
        amazonSQS = sqs;
        createQueue(createQueueRequest);
    }

    protected void createQueue(CreateQueueRequest createQueueRequest){
        if(amazonSQS.listQueues(createQueueRequest.getQueueName()).getQueueUrls().isEmpty()){
            this.QUEUE_NAME = createQueueRequest.getQueueName();
            this.QUEUE_URL = amazonSQS.createQueue(createQueueRequest).getQueueUrl();
        }
        else{
            this.QUEUE_URL = amazonSQS.listQueues(createQueueRequest.getQueueName()).getQueueUrls().get(0);
        }
    }

    public static SQSMessageProducer build(CreateQueueRequest createQueueRequest, AmazonSQS sqsClient){
        return new SQSMessageProducer(createQueueRequest, sqsClient);
    }
}
