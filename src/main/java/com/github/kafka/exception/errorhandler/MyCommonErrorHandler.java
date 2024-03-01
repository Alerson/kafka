package com.github.kafka.exception.errorhandler;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.KafkaUtils;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MyCommonErrorHandler implements CommonErrorHandler {

    @Override
    public boolean handleOne(Exception thrownException, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
        // Log the exception and the problematic record
        System.err.println("Error processing record: " + record.value().toString());
        System.out.println(thrownException.getMessage());
        // Returning true indicates the error handler has fully handled the exception and the listener can continue
        return true;
    }

}




