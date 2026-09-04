package com.github.kafka.consumer;

import com.github.kafka.converter.EnderecoConverter;
import com.github.kafka.dto.EnderecoDTO;
import example.avro.Endereco;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "${spring.kafka.consumer.topic.endereco}",
        groupId = "${spring.kafka.consumer.group-id}",
        errorHandler = "customKafkaErrorHandler")
public class ConsumerEndereco extends AbstractKafkaConsumer<Endereco, EnderecoDTO> {

    private final EnderecoConverter enderecoConverter;

    public ConsumerEndereco(EnderecoConverter enderecoConverter) {
        this.enderecoConverter = enderecoConverter;
    }

    @KafkaHandler
    public void consumer(@Payload Endereco endereco,
                         Acknowledgment ack) {
        processMessage(endereco, ack);
    }

    @Override
    protected EnderecoDTO toDto(Endereco endereco) {
        return enderecoConverter.toDto(endereco);
    }

}
