package org.localhost.pizzeria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
public class PizzeriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzeriaApplication.class, args);
        System.out.println("Pizzeria application started");
    }

}
