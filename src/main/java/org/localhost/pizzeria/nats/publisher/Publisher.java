package org.localhost.pizzeria.nats.publisher;

import io.nats.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.localhost.pizzeria.nats.utils.ObjectSerializer;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class Publisher {
    private final Connection natsConnection;

    public Publisher(Connection natsConnection) {
        this.natsConnection = natsConnection;
    }

    public void publish(Object message, String subject) {
        try {
            byte[] serializedMessage = ObjectSerializer.serialize(message);
            natsConnection.publish(subject, serializedMessage);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
