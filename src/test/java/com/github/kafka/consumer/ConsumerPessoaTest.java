package com.github.kafka.consumer;

import com.github.kafka.converter.PessoaConverter;
import com.github.kafka.dto.PessoaDTO;
import example.avro.Pessoa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.support.Acknowledgment;

public class ConsumerPessoaTest {

    @InjectMocks
    private ConsumerPessoa consumerPessoa;

    @Mock
    private Acknowledgment ack;
    @Mock
    private PessoaConverter pessoaConverter;
    @Mock
    private PessoaDTO pessoaDTO;
    @Mock
    private Pessoa pessoa;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        pessoaDTO = PessoaDTO.builder().surname("Rigo").name("Alerson").build();
        pessoa = Pessoa.newBuilder().setName("Alerson").setSurname("Rigo").build();

        Mockito.when(pessoaConverter.toDto(Mockito.any())).thenReturn(pessoaDTO);
    }

    @Test
    public void testConsumerPessoa() {
        consumerPessoa.consumer(pessoa, ack);
        Assertions.assertNotNull(pessoaDTO, "pessoaDTO should not be null after being processed");
    }
}
