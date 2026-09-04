package com.github.kafka.producer;

import example.avro.Endereco;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerEndereco {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic;

    public ProducerEndereco(KafkaTemplate<String, Object> kafkaTemplate,
                            @Value("${spring.kafka.consumer.topic.endereco}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendMessage(Endereco endereco) {
        kafkaTemplate.send(topic, endereco);
    }
}
