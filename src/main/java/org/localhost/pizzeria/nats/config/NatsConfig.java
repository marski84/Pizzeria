package org.localhost.pizzeria.nats.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Duration;

@Configuration
@Slf4j
public class NatsConfig {
    @Value("${nats.url}")
    private String natsUrl;

    @Bean
    public Connection natsConnection() throws IOException, InterruptedException {
        Options connectionOption = new Options.Builder()
                .server(natsUrl)
                .reconnectWait(Duration.ofSeconds(2))
                .maxReconnects(5)
                .build();

        return Nats.connect(connectionOption);
    }
}
