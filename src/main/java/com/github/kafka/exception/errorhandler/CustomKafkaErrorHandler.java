package com.github.kafka.exception.errorhandler;

import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class CustomKafkaErrorHandler implements KafkaListenerErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomKafkaErrorHandler.class);

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        return null;
    }

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer, Acknowledgment ack) {
        log.error("Error handling message: {} due to: {}", message.getPayload(), exception.getCause());
        ack.acknowledge();
        return null;
    }
}
