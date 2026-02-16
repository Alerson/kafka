package com.github.kafka.consumer;

import com.github.kafka.converter.PessoaConverter;
import com.github.kafka.utils.ValidationUtils;
import example.avro.Pessoa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "${spring.kafka.consumer.topic.pessoa}",
        groupId = "${spring.kafka.consumer.group-id}",
        errorHandler = "customKafkaErrorHandler")
public class ConsumerPessoa {

    private static final Logger log = LoggerFactory.getLogger(ConsumerPessoa.class);

    private final PessoaConverter pessoaConverter;

    public ConsumerPessoa(PessoaConverter pessoaConverter) {
        this.pessoaConverter = pessoaConverter;
    }

    @KafkaHandler
    public void consumer(@Payload Pessoa pessoa,
                         Acknowledgment ack) {
        var pessoaDto = pessoaConverter.toDto(pessoa);
        ValidationUtils.validate(pessoaDto);
        log.info("Kafka message listener: {}", pessoaDto);
        ack.acknowledge();
    }

}
