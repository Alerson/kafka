#!/bin/bash
# to send message
aws --endpoint-url=http://localhost:4566 sqs send-message --queue-url "http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/my-queue-name" --message-body "Your message text here" --region us-east-1

# to create queue
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name my-queue-name --region us-east-1

# to list queue
aws --endpoint-url=http://localhost:4566 sqs list-queues --region us-east-1