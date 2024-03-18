package com.github.kafka.consumer;

import com.github.kafka.converter.ProdutoConverter;
import com.github.kafka.dto.ProdutoDTO;
import example.avro.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.support.Acknowledgment;

class ConsumerProdutoTest {

    private static ConsumerProduto consumerProduto;

    private static Acknowledgment ack;

    private static ProdutoConverter produtoConverter;

    private static ProdutoDTO produtoDTO;

    private static Produto produto;
    @BeforeAll
    public static void setUp(){
        ack = Mockito.mock(Acknowledgment.class);
        produtoConverter = Mockito.mock(ProdutoConverter.class);
        consumerProduto = Mockito.mock(ConsumerProduto.class);

        produtoDTO = ProdutoDTO.builder().name("JBL").valor("2.700").build();
        produto = Produto.newBuilder().setName("JBL").setValor(2.700).build();

        Mockito.when(produtoConverter.toDTO(Mockito.any())).thenReturn(produtoDTO);
    }
    @Test
    public void testConsumerProduto(){
        consumerProduto.consumer(produto, ack);
        Assertions.assertNotNull(produtoDTO, "ProdutoDTO should not be null after being processed");
    }

}