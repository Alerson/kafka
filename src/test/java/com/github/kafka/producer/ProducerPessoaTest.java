package com.github.kafka.producer;

import example.avro.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class ProducerPessoaTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private ProducerPessoa producerPessoa;

    @Value("${spring.kafka.consumer.topic.pessoa}")
    private String topic;

    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pessoa = Pessoa.newBuilder().setName("Alerson").setSurname("Rigo").build();
    }

    @Test
    void sendMessage_sendsMessageToKafkaTopic() {
        producerPessoa.sendMessage(pessoa);
        verify(kafkaTemplate).send(eq(topic), eq(pessoa));
    }
}
