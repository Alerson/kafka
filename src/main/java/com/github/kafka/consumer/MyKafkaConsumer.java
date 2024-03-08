package com.github.kafka.consumer;

import com.github.kafka.converter.PessoaConverter;
import com.github.kafka.dto.PessoaDTO;
import com.github.kafka.utils.ValidationUtils;
import example.avro.Pessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyKafkaConsumer {

    private final PessoaConverter pessoaConverter;

    @KafkaListener(topics = "${spring.kafka.consumer.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            errorHandler = "customKafkaErrorHandler")
    public void consumer(@Payload Pessoa pessoa,
                         Acknowledgment ack) {
        PessoaDTO pessoaDto = pessoaConverter.toDto(pessoa);
        ValidationUtils.validate(pessoaDto);
        System.out.println("Kafka message listener: " + pessoaDto);
        ack.acknowledge();
    }

}
