package com.github.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MyKafkaConsumer {

    @KafkaListener(topics = "${spring.kafka.consumer.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void consumer(String message) {
        System.out.println("Received message: " + message);
        if (message.contains("error")) {
            throw new RuntimeException("Test error");
        }
    }

}
