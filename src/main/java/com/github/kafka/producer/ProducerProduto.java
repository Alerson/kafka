package com.github.kafka.producer;

import example.avro.Produto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerProduto {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String topic;

    public ProducerProduto(KafkaTemplate<String, Object> kafkaTemplate,
                           @Value("${spring.kafka.consumer.topic.produto}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendMessage(Produto produto) {
        kafkaTemplate.send(topic, produto);
    }
}
