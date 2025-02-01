package org.localhost.pizzeria.messaging.config;

import lombok.Value;

@Value
public class RabbitMQConfig {
    String host;
    int port;
    String username;
    String password;
    String supplyCheckQueue;
    String supplyCheckExchange;
    String supplyCheckRoutingKey;
    String dlqQueue;
    String dlqExchange;

    public static RabbitMQConfig getDefaultConfig() {
        return new RabbitMQConfig(
                "localhost",
                5672,
                "guest",
                "guest",
                "supply-check-queue",
                "supply-check-exchange",
                "supply-check-routing-key",
                "supply-check-dlq",
                "supply-check-dlq-exchange"
        );
    }
}