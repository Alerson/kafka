package com.github.kafka.producer;

import example.avro.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class ProducerProdutoTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    private ProducerProduto producerProduto;

    private Produto produto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        producerProduto = new ProducerProduto(kafkaTemplate, "topic-produto");
        produto = Produto.newBuilder().setName("JBL").setValor(2.700).build();
    }

    @Test
    void shouldSendMessageToKafkaTopic() {
        producerProduto.sendMessage(produto);

        verify(kafkaTemplate).send(eq("topic-produto"), eq(produto));
    }
}
