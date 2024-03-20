package com.github.kafka.converter;

import com.github.kafka.dto.ProdutoDTO;
import example.avro.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoConverterTest {
    @InjectMocks
    private ProdutoConverter produtoConverter;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testProdutoToDto(){
        ProdutoDTO dto = produtoConverter.toDTO(Produto.newBuilder().setName("JBL").setValor(2.700).build());
        Assertions.assertNotNull(dto, "dto should not be null after being processed");
    }

}