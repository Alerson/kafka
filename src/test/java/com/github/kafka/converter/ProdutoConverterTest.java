package com.github.kafka.converter;

import com.github.kafka.dto.ProdutoDTO;
import example.avro.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProdutoConverterTest {

    public static ProdutoConverter produtoConverter;

    public static ProdutoDTO produtoDTO;

    public static Produto produto;
    @BeforeAll
    public static void setUp(){
        produtoConverter = Mockito.mock(ProdutoConverter.class);

        produtoDTO = ProdutoDTO.builder().name("JBL").valor("2.700").build();
        produto = Produto.newBuilder().setName("JBL").setValor(2.700).build();

        Mockito.when(produtoConverter.toDTO(produto)).thenReturn(produtoDTO);
    }
    @Test
    public void testProdutoConverterDto(){
        ProdutoDTO dto = produtoConverter.toDTO(produto);
        Assertions.assertNotNull(dto, "dto should not be null after being processed");
    }

}