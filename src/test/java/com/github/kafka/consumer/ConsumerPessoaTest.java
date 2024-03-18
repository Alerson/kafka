package com.github.kafka.consumer;

import com.github.kafka.converter.PessoaConverter;
import com.github.kafka.dto.PessoaDTO;
import example.avro.Pessoa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.support.Acknowledgment;

public class ConsumerPessoaTest {

    private static ConsumerPessoa consumerPessoa;

    private static Acknowledgment ack;
    private static PessoaConverter pessoaConverter;

    private static PessoaDTO pessoaDTO;
    private static Pessoa pessoa;

    @BeforeAll
    public static void setUp() {
        ack = Mockito.mock(Acknowledgment.class);
        pessoaConverter = Mockito.mock(PessoaConverter.class);
        consumerPessoa = new ConsumerPessoa(pessoaConverter);

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
