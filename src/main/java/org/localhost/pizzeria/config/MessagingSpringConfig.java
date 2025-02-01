package org.localhost.pizzeria.config;


import org.localhost.pizzeria.messaging.config.RabbitMQConfig;
import org.localhost.pizzeria.messaging.initializer.RabbitMQInitializer;
import org.localhost.pizzeria.messaging.publisher.RabbitMQPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class MessagingSpringConfig {

    @Bean
    public RabbitMQConfig rabbitMQConfig() {
        return RabbitMQConfig.getDefaultConfig();
    }

    @Bean
    public RabbitMQPublisher rabbitMQPublisher(RabbitMQConfig config) throws IOException, TimeoutException {
        return new RabbitMQPublisher(config);
    }

    @Bean
    public RabbitMQInitializer rabbitMQInitializer(RabbitMQConfig config) {
        return new RabbitMQInitializer(config);
    }
}