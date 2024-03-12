package com.github.kafka.sqs;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.annotation.SqsListenerAcknowledgementMode;
import org.springframework.stereotype.Service;

@Service
public class SQSMessageListener {

    @SqsListener(value = "${app.sqs.queue-name}", acknowledgementMode = SqsListenerAcknowledgementMode.ALWAYS)
    public void listen(String message) throws Exception {
        System.out.println("SQS Message listener: " + message);
    }

}
