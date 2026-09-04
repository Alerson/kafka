package com.github.kafka.consumer;

import com.github.kafka.converter.PessoaConverter;
import com.github.kafka.dto.PessoaDTO;
import example.avro.Pessoa;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "${spring.kafka.consumer.topic.pessoa}",
        groupId = "${spring.kafka.consumer.group-id}",
        errorHandler = "customKafkaErrorHandler")
public class ConsumerPessoa extends AbstractKafkaConsumer<Pessoa, PessoaDTO> {

    private final PessoaConverter pessoaConverter;

    public ConsumerPessoa(PessoaConverter pessoaConverter) {
        this.pessoaConverter = pessoaConverter;
    }

    @KafkaHandler
    public void consumer(@Payload Pessoa pessoa,
                         Acknowledgment ack) {
        processMessage(pessoa, ack);
    }

    @Override
    protected PessoaDTO toDto(Pessoa pessoa) {
        return pessoaConverter.toDto(pessoa);
    }

}
