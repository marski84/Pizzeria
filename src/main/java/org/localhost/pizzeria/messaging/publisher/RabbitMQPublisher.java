package org.localhost.pizzeria.messaging.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.localhost.pizzeria.messaging.config.RabbitMQConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class RabbitMQPublisher implements AutoCloseable {
    private final Connection connection;
    private final Channel channel;
    private final ObjectMapper objectMapper;

    public RabbitMQPublisher(RabbitMQConfig config) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(config.getHost());
        factory.setPort(config.getPort());
        factory.setUsername(config.getUsername());
        factory.setPassword(config.getPassword());

        this.connection = factory.newConnection();
        this.channel = connection.createChannel();
        this.objectMapper = new ObjectMapper();
    }

    public void publishMessage(String exchange, String routingKey, Object message) throws IOException {
        log.info("Publishing to exchange: {}, routing key: {}", exchange, routingKey);

        try {
            byte[] messageBytes = objectMapper.writeValueAsBytes(message);
            System.out.println(messageBytes.length);
            log.info("Serialized message: {}", new String(messageBytes));

            channel.basicPublish(exchange,
                    routingKey,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    messageBytes);
        } catch (Exception e) {
            log.error("Error publishing message: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void close() throws Exception {
        if (channel != null && channel.isOpen()) {
            channel.close();
        }
        if (connection != null && connection.isOpen()) {
            connection.close();
        }
    }
}
