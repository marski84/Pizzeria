package org.localhost.pizzeria.supplies.system.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.localhost.pizzeria.messaging.dto.SupplyCheckMessage;
import org.localhost.pizzeria.messaging.publisher.RabbitMQPublisher;
import org.localhost.pizzeria.supplies.system.model.Ingredient;
import org.localhost.pizzeria.supplies.system.service.SupplySystemQueryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
@Slf4j
public class SupplyCheckScheduler {
    private final SupplySystemQueryService supplySystemQueryService;
    private final RabbitMQPublisher rabbitMQPublisher;


    @Value("${rabbitmq.exchange.supply-check}")
    private String supplyCheckExchange;

    @Value("${rabbitmq.routing-key.supply-check}")
    private String supplyCheckRoutingKey;


    public SupplyCheckScheduler(SupplySystemQueryService supplySystemQueryService, RabbitMQPublisher rabbitMQPublisher) {
        this.supplySystemQueryService = supplySystemQueryService;
        this.rabbitMQPublisher = rabbitMQPublisher;
    }

//    @Scheduled(fixedDelayString = "${scheduler.supply-check.interval}")
    @Scheduled(cron = "* * * * * *")
    public void checkSuppliesStatus() {
        log.info("Checking supplies status");
        try {
            List<Ingredient> suppliesToOrderList = supplySystemQueryService.getIngredientsStockStatus().stream()
                    .filter(Ingredient::needsRestock)
                    .toList();

            log.info("Number of ingredients to supply: {}", suppliesToOrderList.size());
            if (!suppliesToOrderList.isEmpty()) {
                SupplyCheckMessage supplyCheckMessage = new SupplyCheckMessage(suppliesToOrderList);
                rabbitMQPublisher.publishMessage(supplyCheckExchange, supplyCheckRoutingKey, supplyCheckMessage);
                log.info("Supply check message sent for ingredients: " + suppliesToOrderList.size());
            }
        } catch (Exception e) {
            log.error("Error while checking supplies status: {}", e.getMessage());
            e.getMessage();
        }
    }

}
