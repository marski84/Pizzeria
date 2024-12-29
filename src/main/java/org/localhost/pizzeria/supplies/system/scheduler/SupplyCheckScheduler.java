package org.localhost.pizzeria.supplies.system.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.localhost.pizzeria.supplies.system.service.SupplySystem;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@Slf4j
public class SupplyCheckScheduler {
    private final SupplySystem supplySystem;

    public SupplyCheckScheduler(SupplySystem supplySystem) {
        this.supplySystem = supplySystem;
    }

    @Scheduled(fixedDelayString = "${scheduler.supply-check.interval}")
    public void checkSuppliesStatus() {
        log.info("Checking supplies status");

        try {
//            supplySystem.checkSuppliesStatus();
        } catch (Exception e) {
            log.error("Error while checking supplies status: {}", e.getMessage());
            e.getMessage();
        }
//        supplySystem.checkSuppliesStatus();
    }

}
