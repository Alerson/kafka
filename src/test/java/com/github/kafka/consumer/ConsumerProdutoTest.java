package com.github.kafka.consumer;

import com.github.kafka.converter.ProdutoConverter;
import com.github.kafka.dto.ProdutoDTO;
import example.avro.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.support.Acknowledgment;

class ConsumerProdutoTest {
    @InjectMocks
    private ConsumerProduto consumerProduto;
    @Mock
    private Acknowledgment ack;
    @Mock
    private ProdutoConverter produtoConverter;

    private ProdutoDTO produtoDTO;

    private Produto produto;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        produtoDTO = ProdutoDTO.builder().name("JBL").valor("2.700").build();
        produto = Produto.newBuilder().setName("JBL").setValor(2.700).build();

        Mockito.when(produtoConverter.toDTO(Mockito.any())).thenReturn(produtoDTO);
    }
    @Test
    public void testConsumerProduto(){
        consumerProduto.consumer(produto, ack);
        Assertions.assertNotNull(produtoDTO, "produtoDTO should not be null after being processed");
    }

}