package com.github.kafka.consumer;

import com.github.kafka.converter.ProdutoConverter;
import com.github.kafka.dto.ProdutoDTO;
import com.github.kafka.utils.ValidationUtils;
import example.avro.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@KafkaListener(topics = "${spring.kafka.consumer.topic.produto}",
        groupId = "${spring.kafka.consumer.group-id}",
        errorHandler = "customKafkaErrorHandler")
public class ConsumerProduto {

    private final ProdutoConverter produtoConverter;

    @KafkaHandler
    public void consumer(@Payload Produto produto,
                         Acknowledgment ack){
        ProdutoDTO produtoDTO = produtoConverter.toDTO(produto);
        ValidationUtils.validate(produtoDTO);
        System.out.println("kafka message listener: "+ produtoDTO);
        ack.acknowledge();
    }
}
