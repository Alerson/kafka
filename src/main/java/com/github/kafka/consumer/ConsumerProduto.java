package com.github.kafka.consumer;

import com.github.kafka.converter.ProdutoConverter;
import com.github.kafka.dto.ProdutoDTO;
import example.avro.Produto;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "${spring.kafka.consumer.topic.produto}",
        groupId = "${spring.kafka.consumer.group-id}",
        errorHandler = "customKafkaErrorHandler")
public class ConsumerProduto extends AbstractKafkaConsumer<Produto, ProdutoDTO> {

    private final ProdutoConverter produtoConverter;

    public ConsumerProduto(ProdutoConverter produtoConverter) {
        this.produtoConverter = produtoConverter;
    }

    @KafkaHandler
    public void consumer(@Payload Produto produto,
                         Acknowledgment ack) {
        processMessage(produto, ack);
    }

    @Override
    protected ProdutoDTO toDto(Produto produto) {
        return produtoConverter.toDto(produto);
    }
}
