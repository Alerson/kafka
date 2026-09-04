package com.github.kafka.converter;

import com.github.kafka.dto.EnderecoDTO;
import example.avro.Endereco;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoConverterTest {

    private final EnderecoConverter enderecoConverter = new EnderecoConverter();

    @Test
    void shouldConvertEnderecoToDto() {
        Endereco endereco = Endereco.newBuilder()
                .setLogradouro("Rua das Flores")
                .setNumero(123)
                .setCidade("Porto Alegre")
                .setEstado("RS")
                .setCep("90000-000")
                .build();

        EnderecoDTO dto = enderecoConverter.toDto(endereco);

        assertNotNull(dto);
        assertEquals("Rua das Flores", dto.getLogradouro());
        assertEquals(123, dto.getNumero());
        assertEquals("Porto Alegre", dto.getCidade());
        assertEquals("RS", dto.getEstado());
        assertEquals("90000-000", dto.getCep());
    }

    @Test
    void shouldReturnEmptyStringWhenTextFieldsAreNullAndKeepNumeroNull() {
        Endereco endereco = Endereco.newBuilder()
                .setLogradouro(null)
                .setNumero(null)
                .setCidade(null)
                .setEstado(null)
                .setCep(null)
                .build();

        EnderecoDTO dto = enderecoConverter.toDto(endereco);

        assertNotNull(dto);
        assertEquals("", dto.getLogradouro());
        assertNull(dto.getNumero());
        assertEquals("", dto.getCidade());
        assertEquals("", dto.getEstado());
        assertEquals("", dto.getCep());
    }
}
