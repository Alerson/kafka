package com.github.kafka.consumer;

import com.github.kafka.converter.PessoaConverter;
import com.github.kafka.dto.PessoaDTO;
import example.avro.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.support.Acknowledgment;

import static org.mockito.Mockito.*;

class ConsumerPessoaTest {

    private ConsumerPessoa consumerPessoa;

    @Mock
    private Acknowledgment ack;

    @Mock
    private PessoaConverter pessoaConverter;

    private PessoaDTO pessoaDTO;
    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consumerPessoa = new ConsumerPessoa(pessoaConverter);
        pessoaDTO = new PessoaDTO("Alerson", "Rigo");
        pessoa = Pessoa.newBuilder().setName("Alerson").setSurname("Rigo").build();

        when(pessoaConverter.toDto(any())).thenReturn(pessoaDTO);
    }

    @Test
    void shouldConsumeMessageAndAcknowledge() {
        consumerPessoa.consumer(pessoa, ack);

        verify(pessoaConverter).toDto(pessoa);
        verify(ack).acknowledge();
    }
}
