package com.github.kafka.converter;

import com.github.kafka.dto.ProdutoDTO;
import example.avro.Produto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoConverterTest {

    private final ProdutoConverter produtoConverter = new ProdutoConverter();

    @Test
    void shouldConvertProdutoToDto() {
        Produto produto = Produto.newBuilder().setName("JBL").setValor(2.700).build();

        ProdutoDTO dto = produtoConverter.toDto(produto);

        assertNotNull(dto);
        assertEquals("JBL", dto.getName());
        assertEquals("2.7", dto.getValor());
    }

    @Test
    void shouldReturnEmptyStringWhenNameIsNull() {
        Produto produto = Produto.newBuilder().setName(null).setValor(2.700).build();

        ProdutoDTO dto = produtoConverter.toDto(produto);

        assertNotNull(dto);
        assertEquals("", dto.getName());
    }

    @Test
    void shouldReturnNullValorWhenValorIsNull() {
        Produto produto = Produto.newBuilder().setName("JBL").setValor(null).build();

        ProdutoDTO dto = produtoConverter.toDto(produto);

        assertNotNull(dto);
        assertNull(dto.getValor());
    }
}
