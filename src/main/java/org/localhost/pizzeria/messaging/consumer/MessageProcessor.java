package org.localhost.pizzeria.messaging.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageProcessor {

    private static final String HEADER_X_RETRIES_COUNT = "x-retries-count";
    private static final int MAX_RETRIES_COUNT = 3;

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.supply-check}")
    private String exchangeName;

    @RabbitListener(queues = "${rabbitmq.queue.supply-check}")
    public void supplyCheck(Message message) throws AmqpRejectAndDontRequeueException {
        throw new AmqpRejectAndDontRequeueException(message.getMessageProperties().getCorrelationId());
    }

    @RabbitListener(queues = "${rabbitmq.queue.supply-check-dlq}")
    public void processFailedMessages(Message failedMessage) {
        processFailedMessagesRetry(failedMessage);
    }

    private void processFailedMessagesRetry(Message failedMessage) {
        Integer retriesCnt = (Integer) failedMessage.getMessageProperties()
                .getHeaders().get(HEADER_X_RETRIES_COUNT);

        if (retriesCnt == null) retriesCnt = 1;
        if (retriesCnt > MAX_RETRIES_COUNT) {
            log.info("Discarding message");
            return;
        }

        log.info("Retrying message for the {} time", retriesCnt);
        failedMessage.getMessageProperties()
                .getHeaders().put(HEADER_X_RETRIES_COUNT, ++retriesCnt);

        rabbitTemplate.send(exchangeName,
                failedMessage.getMessageProperties().getReceivedRoutingKey(),
                failedMessage);
    }
}