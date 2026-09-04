package com.github.kafka.consumer;

import com.github.kafka.converter.EnderecoConverter;
import com.github.kafka.utils.ValidationUtils;
import example.avro.Endereco;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "${spring.kafka.consumer.topic.endereco}",
        groupId = "${spring.kafka.consumer.group-id}",
        errorHandler = "customKafkaErrorHandler")
public class ConsumerEndereco {

    private static final Logger log = LoggerFactory.getLogger(ConsumerEndereco.class);

    private final EnderecoConverter enderecoConverter;

    public ConsumerEndereco(EnderecoConverter enderecoConverter) {
        this.enderecoConverter = enderecoConverter;
    }

    @KafkaHandler
    public void consumer(@Payload Endereco endereco,
                         Acknowledgment ack) {
        var enderecoDTO = enderecoConverter.toDto(endereco);
        ValidationUtils.validate(enderecoDTO);
        log.info("Kafka message listener: {}", enderecoDTO);
        ack.acknowledge();
    }

}
