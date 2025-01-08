package org.localhost.pizzeria.messaging.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SupplyCheckConsumer {

    @RabbitListener(queues = "${rabbitmq.queue.supply-check}")
    public void consumeSupplyCheck(Message message) throws InterruptedException {
        Thread.sleep(60000); // 5 sekund opóźnienia

        log.info("Consumed message {}", message);


    }
}
