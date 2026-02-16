package com.github.kafka.converter;

import com.github.kafka.dto.PessoaDTO;
import example.avro.Pessoa;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaConverterTest {

    private final PessoaConverter pessoaConverter = new PessoaConverter();

    @Test
    void shouldConvertPessoaToDto() {
        Pessoa pessoa = Pessoa.newBuilder().setName("Alerson").setSurname("Rigo").build();

        PessoaDTO dto = pessoaConverter.toDto(pessoa);

        assertNotNull(dto);
        assertEquals("Alerson", dto.getName());
        assertEquals("Rigo", dto.getSurname());
    }

    @Test
    void shouldReturnEmptyStringWhenFieldsAreNull() {
        Pessoa pessoa = Pessoa.newBuilder().setName(null).setSurname(null).build();

        PessoaDTO dto = pessoaConverter.toDto(pessoa);

        assertNotNull(dto);
        assertEquals("", dto.getName());
        assertEquals("", dto.getSurname());
    }
}
