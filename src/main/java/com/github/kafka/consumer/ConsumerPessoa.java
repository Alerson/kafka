package com.github.kafka.consumer;

import com.github.kafka.converter.PessoaConverter;
import com.github.kafka.dto.PessoaDTO;
import com.github.kafka.utils.ValidationUtils;
import example.avro.Pessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@KafkaListener(topics = "${spring.kafka.consumer.topic.pessoa}",
        groupId = "${spring.kafka.consumer.group-id}",
        errorHandler = "customKafkaErrorHandler")
public class ConsumerPessoa {

    private final PessoaConverter pessoaConverter;

    @KafkaHandler
    public void consumer(@Payload Pessoa pessoa,
                         Acknowledgment ack) {
        var pessoaDto = pessoaConverter.toDto(pessoa);
        ValidationUtils.validate(pessoaDto);
        System.out.println("Kafka message listener: " + pessoaDto);
        ack.acknowledge();
    }

}
