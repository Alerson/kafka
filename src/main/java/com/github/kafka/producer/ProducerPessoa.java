package com.github.kafka.producer;

import example.avro.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerPessoa {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.consumer.topic.pessoa}")
    private String topic;

    public void sendMessage(Pessoa pessoa) {
        kafkaTemplate.send(topic, pessoa);
    }
}
