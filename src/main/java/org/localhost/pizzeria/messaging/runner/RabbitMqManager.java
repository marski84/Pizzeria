package org.localhost.pizzeria.messaging.runner;

import org.localhost.pizzeria.messaging.config.RabbitMQConfig;
import org.localhost.pizzeria.messaging.initializer.RabbitMQInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RabbitMqManager {
    private static final Logger log = LoggerFactory.getLogger(RabbitMqManager.class);

    public static void main(Class<RabbitMqManager> args) {
        try {
            RabbitMQConfig config = RabbitMQConfig.getDefaultConfig();
            RabbitMQInitializer initializer = new RabbitMQInitializer(config);
            initializer.initialize();
            log.info("RabbitMQ queues initialized successfully");
        } catch (Exception e) {
            log.error("Failed to initialize RabbitMQ queues", e);
        }
    }
}