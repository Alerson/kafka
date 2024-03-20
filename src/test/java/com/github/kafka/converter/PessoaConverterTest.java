package com.github.kafka.converter;

import com.github.kafka.dto.PessoaDTO;
import example.avro.Pessoa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class PessoaConverterTest {
    @InjectMocks
    private PessoaConverter pessoaConverter;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testPessoaToDto(){
        PessoaDTO dto = pessoaConverter.toDto(Pessoa.newBuilder().setName("Alerson").setSurname("Rigo").build());
        Assertions.assertNotNull(dto, "dto should not be null after being processed");
    }

}