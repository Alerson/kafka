package com.github.kafka.converter;

import com.github.kafka.dto.PessoaDTO;
import example.avro.Pessoa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PessoaConverterTest {

    private static PessoaConverter pessoaConverter;

    private static Pessoa pessoa;

    private static PessoaDTO pessoaDTO;
    @BeforeAll
    public static void setUp(){
        pessoaConverter = Mockito.mock(PessoaConverter.class);

        pessoa = Pessoa.newBuilder().setName("Alerson").setSurname("Rigo").build();
        pessoaDTO = PessoaDTO.builder().name("Alerson").surname("Rigo").build();

        Mockito.when(pessoaConverter.toDto(pessoa)).thenReturn(pessoaDTO);
    }
    @Test
    public void testPessoaConverterDto(){
        PessoaDTO dto = pessoaConverter.toDto(pessoa);
        Assertions.assertNotNull(dto, "dto should not be null after being processed");
    }
}