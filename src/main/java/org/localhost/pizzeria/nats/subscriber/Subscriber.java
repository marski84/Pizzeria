package org.localhost.pizzeria.nats.subscriber;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import lombok.extern.slf4j.Slf4j;
import org.localhost.pizzeria.nats.utils.ObjectSerializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Subscriber implements InitializingBean {
    private final Connection natsConnection;
    private Dispatcher dispatcher;


    public Subscriber(Connection natsConnection) {
        this.natsConnection = natsConnection;
    }

    public void subscribe(String subject) {
        dispatcher.subscribe(subject);
        log.info("Subscribed to NATS subject: {}", subject);
    }

    @Override
    public void afterPropertiesSet() {
        dispatcher = natsConnection.createDispatcher((msg) -> {
            try {
                Object deserializedMessage = ObjectSerializer.deserialize(msg.getData(), Object.class);
                processMessage(deserializedMessage);
            } catch (Exception e) {
                log.error("Error processing NATS message", e);
            }
        });
    }

    private void processMessage(Object message) {
        log.info("Processed message: {}", message);
    }

    }
