package com.github.kafka.consumer;

import com.github.kafka.converter.ProdutoConverter;
import com.github.kafka.dto.ProdutoDTO;
import example.avro.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.support.Acknowledgment;

import static org.mockito.Mockito.*;

class ConsumerProdutoTest {

    private ConsumerProduto consumerProduto;

    @Mock
    private Acknowledgment ack;

    @Mock
    private ProdutoConverter produtoConverter;

    private ProdutoDTO produtoDTO;
    private Produto produto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consumerProduto = new ConsumerProduto(produtoConverter);
        produtoDTO = new ProdutoDTO("JBL", "2.700");
        produto = Produto.newBuilder().setName("JBL").setValor(2.700).build();

        when(produtoConverter.toDto(any())).thenReturn(produtoDTO);
    }

    @Test
    void shouldConsumeMessageAndAcknowledge() {
        consumerProduto.consumer(produto, ack);

        verify(produtoConverter).toDto(produto);
        verify(ack).acknowledge();
    }
}
