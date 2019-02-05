package com.example.challenge.moodTracker.integration;

public interface ProducerFactory {

    MessageProducer getProducer(String producerName);
}
