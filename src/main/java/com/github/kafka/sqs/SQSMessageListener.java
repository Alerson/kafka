package com.github.kafka.sqs;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.annotation.SqsListenerAcknowledgementMode;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
public class SQSMessageListener {

    @SqsListener(value = "${app.sqs.queue-name}", acknowledgementMode = SqsListenerAcknowledgementMode.ALWAYS)
    public void listen(String message) throws Exception {
        System.out.println("SQS Message listener: " + message);
    }

}
