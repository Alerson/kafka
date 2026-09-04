package com.github.kafka.consumer;

import com.github.kafka.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;

/**
 * Template Method: define o esqueleto comum de processamento de mensagens Kafka
 * (converter payload Avro -> DTO, validar, logar, acknowledge), deixando cada
 * consumer concreto responsável apenas por dizer *como* converter seu payload.
 * <p>
 * Extraído após {@code ConsumerPessoa}, {@code ConsumerProduto} e {@code ConsumerEndereco}
 * duplicarem exatamente o mesmo fluxo (regra dos três). Ver
 * {@code .claude/skills/design-patterns/SKILL.md} - seção Template Method.
 *
 * @param <T> tipo do payload Avro recebido do tópico
 * @param <D> tipo do DTO validado após a conversão
 */
public abstract class AbstractKafkaConsumer<T, D> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    protected final void processMessage(T payload, Acknowledgment ack) {
        D dto = toDto(payload);
        ValidationUtils.validate(dto);
        log.info("Kafka message listener: {}", dto);
        ack.acknowledge();
    }

    protected abstract D toDto(T payload);

}
