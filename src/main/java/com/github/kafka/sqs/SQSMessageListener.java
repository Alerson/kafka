package com.github.kafka.sqs;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.annotation.SqsListenerAcknowledgementMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SQSMessageListener {

    private static final Logger log = LoggerFactory.getLogger(SQSMessageListener.class);

    @SqsListener(value = "${app.sqs.queue-name}", acknowledgementMode = SqsListenerAcknowledgementMode.ALWAYS)
    public void listen(String message) {
        try {
            log.info("SQS Message listener: {}", message);
        } catch (Exception e) {
            log.error("Error processing SQS message", e);
        }
    }

}
