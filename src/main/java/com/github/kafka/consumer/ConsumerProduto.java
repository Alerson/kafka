package com.github.kafka.consumer;

import com.github.kafka.converter.ProdutoConverter;
import com.github.kafka.utils.ValidationUtils;
import example.avro.Produto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "${spring.kafka.consumer.topic.produto}",
        groupId = "${spring.kafka.consumer.group-id}",
        errorHandler = "customKafkaErrorHandler")
public class ConsumerProduto {

    private static final Logger log = LoggerFactory.getLogger(ConsumerProduto.class);

    private final ProdutoConverter produtoConverter;

    public ConsumerProduto(ProdutoConverter produtoConverter) {
        this.produtoConverter = produtoConverter;
    }

    @KafkaHandler
    public void consumer(@Payload Produto produto,
                         Acknowledgment ack) {
        var produtoDTO = produtoConverter.toDto(produto);
        ValidationUtils.validate(produtoDTO);
        log.info("Kafka message listener: {}", produtoDTO);
        ack.acknowledge();
    }
}
