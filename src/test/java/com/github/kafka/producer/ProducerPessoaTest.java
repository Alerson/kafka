package com.github.kafka.producer;

import example.avro.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class ProducerPessoaTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    private ProducerPessoa producerPessoa;

    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        producerPessoa = new ProducerPessoa(kafkaTemplate, "topic-pessoa");
        pessoa = Pessoa.newBuilder().setName("Alerson").setSurname("Rigo").build();
    }

    @Test
    void shouldSendMessageToKafkaTopic() {
        producerPessoa.sendMessage(pessoa);

        verify(kafkaTemplate).send(eq("topic-pessoa"), eq(pessoa));
    }
}
