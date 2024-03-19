package com.github.kafka.producer;

import example.avro.Pessoa;
import example.avro.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class ProducerProdutoTest {
    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private ProducerProduto producerProduto;

    @Value("${spring.kafka.consumer.topic.produto}")
    private String topic;

    private Produto produto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        produto = Produto.newBuilder().setName("JBL").setValor(2.700).build();
    }

    @Test
    void sendMessage_sendsMessageToKafkaTopic() {
        producerProduto.sendMessage(produto);
        verify(kafkaTemplate).send(eq(topic), eq(produto));
    }

}