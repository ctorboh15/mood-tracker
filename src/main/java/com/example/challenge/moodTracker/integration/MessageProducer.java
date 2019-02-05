package com.example.challenge.moodTracker.integration;

public interface MessageProducer {

    void sendMessage(Object message) throws Exception;

    String getQueueName();
    String getQueueUrl();
}
