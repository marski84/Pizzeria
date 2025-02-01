package org.localhost.pizzeria;

import org.localhost.pizzeria.messaging.runner.RabbitMqManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PizzeriaApplication {

    public static void main(String[] args) {
        RabbitMqManager.main(RabbitMqManager.class);
        SpringApplication.run(PizzeriaApplication.class, args);
        System.out.println("Pizzeria application started");
    }

}
