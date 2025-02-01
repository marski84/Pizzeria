package org.localhost.pizzeria.messaging.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.localhost.pizzeria.messaging.dto.SupplyCheckMessage;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SupplyRestockConsumer {

    private final ObjectMapper objectMapper;

    public SupplyRestockConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @RabbitListener(queues = "${rabbitmq.queue.supply-check}")
    public void consumeSupplyCheck(Message message) {
        try {
            log.info("Received supply check message");
            String messageBody = new String(message.getBody());
            log.info("Message content: {}", messageBody);

            SupplyCheckMessage suppliesToOrderList = objectMapper.readValue(
                    message.getBody(),
                    SupplyCheckMessage.class
            );
            // TODO: logika obsługi wiadomości

        } catch (Exception e) {
            log.error("Error processing supply check message: {}", e.getMessage(), e);
            throw new AmqpRejectAndDontRequeueException("Failed to process message", e);
        }
    }
}
