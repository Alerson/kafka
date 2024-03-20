package com.github.kafka.exception.errorhandler;

import org.apache.kafka.clients.consumer.Consumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;

import static org.mockito.Mockito.*;

public class CustomKafkaErrorHandlerTest {

    @Mock
    private Message<String> mockMessage;
    @Mock
    private ListenerExecutionFailedException mockException;
    @Mock
    private Consumer<?, ?> mockConsumer;
    @Mock
    private Acknowledgment mockAck;
    @InjectMocks
    private CustomKafkaErrorHandler errorHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void handleErrorWithoutConsumerReturnsNull() {
        Object result = errorHandler.handleError(mockMessage, mockException);
        Assertions.assertNull(result);
    }

    @Test
    public void handleErrorWithConsumerAcknowledgesMessage() {
        when(mockMessage.getPayload()).thenReturn("Test message");
        when(mockException.getCause()).thenReturn(new RuntimeException("Test exception"));

        Object result = errorHandler.handleError(mockMessage, mockException, mockConsumer, mockAck);

        verify(mockAck, times(1)).acknowledge();
        Assertions.assertNull(result);
    }
}
