package com.github.kafka.producer;

import example.avro.Pessoa;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerPessoa {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic;

    public ProducerPessoa(KafkaTemplate<String, Object> kafkaTemplate,
                          @Value("${spring.kafka.consumer.topic.pessoa}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendMessage(Pessoa pessoa) {
        kafkaTemplate.send(topic, pessoa);
    }
}
