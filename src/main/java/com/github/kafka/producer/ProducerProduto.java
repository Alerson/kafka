package com.github.kafka.producer;

import example.avro.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerProduto {


    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.consumer.topic.produto}")
    private String topic;

    public void sendMessage(Produto pessoa) {
        kafkaTemplate.send(topic, pessoa);
    }
}
