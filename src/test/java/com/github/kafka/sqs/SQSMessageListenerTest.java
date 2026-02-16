package com.github.kafka.sqs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class SQSMessageListenerTest {

    private final SQSMessageListener listener = new SQSMessageListener();

    @Test
    void shouldListenToMessage() {
        assertDoesNotThrow(() -> listener.listen("Test SQS message"));
    }
}
