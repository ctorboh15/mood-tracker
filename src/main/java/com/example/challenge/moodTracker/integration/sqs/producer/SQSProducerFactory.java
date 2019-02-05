package com.example.challenge.moodTracker.integration.sqs.producer;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.example.challenge.moodTracker.integration.MessageProducer;
import com.example.challenge.moodTracker.integration.ProducerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class SQSProducerFactory implements ProducerFactory {

    @Autowired private AmazonSQS amazonSQS;
    private HashMap<String, MessageProducer> producerHashMap;

    public SQSProducerFactory(){
        producerHashMap = new HashMap<>();
    }

    @Override
    public MessageProducer getProducer(String producerName) {
        if(producerHashMap.containsKey(producerName)){
            return producerHashMap.get(producerName);
        }

        producerHashMap.put(producerName, SQSMessageProducer.build(new CreateQueueRequest(producerName + "_Queue"), amazonSQS));
        return producerHashMap.get(producerName);
    }
}