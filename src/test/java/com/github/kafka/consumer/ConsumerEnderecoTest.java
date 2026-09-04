package com.github.kafka.consumer;

import com.github.kafka.converter.EnderecoConverter;
import com.github.kafka.dto.EnderecoDTO;
import example.avro.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.support.Acknowledgment;

import static org.mockito.Mockito.*;

class ConsumerEnderecoTest {

    private ConsumerEndereco consumerEndereco;

    @Mock
    private Acknowledgment ack;

    @Mock
    private EnderecoConverter enderecoConverter;

    private EnderecoDTO enderecoDTO;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consumerEndereco = new ConsumerEndereco(enderecoConverter);
        enderecoDTO = new EnderecoDTO("Rua das Flores", 123, "Porto Alegre", "RS", "90000-000");
        endereco = Endereco.newBuilder()
                .setLogradouro("Rua das Flores")
                .setNumero(123)
                .setCidade("Porto Alegre")
                .setEstado("RS")
                .setCep("90000-000")
                .build();

        when(enderecoConverter.toDto(any())).thenReturn(enderecoDTO);
    }

    @Test
    void shouldConsumeMessageAndAcknowledge() {
        consumerEndereco.consumer(endereco, ack);

        verify(enderecoConverter).toDto(endereco);
        verify(ack).acknowledge();
    }
}
