package org.localhost.pizzeria.messaging.initializer;

import com.rabbitmq.client.*;
import org.localhost.pizzeria.messaging.config.RabbitMQConfig;

import java.util.Map;

public class RabbitMQInitializer {
    private final String host;
    private final int port;
    private final String username;
    private final String password;

    private final String supplyCheckQueue;
    private final String supplyCheckExchange;
    private final String supplyCheckRoutingKey;
    private final String dlqQueue;
    private final String dlqExchange;

    public RabbitMQInitializer(RabbitMQConfig config) {
        this.host = config.getHost();
        this.port = config.getPort();
        this.username = config.getUsername();
        this.password = config.getPassword();
        this.supplyCheckQueue = config.getSupplyCheckQueue();
        this.supplyCheckExchange = config.getSupplyCheckExchange();
        this.supplyCheckRoutingKey = config.getSupplyCheckRoutingKey();
        this.dlqQueue = config.getDlqQueue();
        this.dlqExchange = config.getDlqExchange();
    }

    public void initialize() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Deklaracja DLX i DLQ
            channel.exchangeDeclare(dlqExchange, BuiltinExchangeType.DIRECT, true);
            channel.queueDeclare(dlqQueue, true, false, false, null);
            channel.queueBind(dlqQueue, dlqExchange, "");

            // Deklaracja głównej wymiany i kolejki
            channel.exchangeDeclare(supplyCheckExchange, BuiltinExchangeType.TOPIC, true);

            AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(
                    supplyCheckQueue,
                    true,  // durable
                    false, // exclusive
                    false, // autoDelete
                    Map.of("x-dead-letter-exchange", dlqExchange)
            );

            channel.queueBind(supplyCheckQueue, supplyCheckExchange, supplyCheckRoutingKey);
        }
    }
}
