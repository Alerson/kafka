package com.github.kafka.producer;

import example.avro.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class ProducerEnderecoTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    private ProducerEndereco producerEndereco;

    private Endereco endereco;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        producerEndereco = new ProducerEndereco(kafkaTemplate, "topic-endereco");
        endereco = Endereco.newBuilder()
                .setLogradouro("Rua das Flores")
                .setNumero(123)
                .setCidade("Porto Alegre")
                .setEstado("RS")
                .setCep("90000-000")
                .build();
    }

    @Test
    void shouldSendMessageToKafkaTopic() {
        producerEndereco.sendMessage(endereco);

        verify(kafkaTemplate).send(eq("topic-endereco"), eq(endereco));
    }
}
