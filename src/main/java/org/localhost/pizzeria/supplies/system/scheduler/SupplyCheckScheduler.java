package org.localhost.pizzeria.supplies.system.scheduler;

import org.localhost.pizzeria.PizzeriaLogger;
import org.localhost.pizzeria.supplies.system.model.Ingredient;
import org.localhost.pizzeria.supplies.system.service.SupplySystemQueryService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class SupplyCheckScheduler {
    private final SupplySystemQueryService supplySystemQueryService;
    private static final PizzeriaLogger log = PizzeriaLogger.getInstance();


    public SupplyCheckScheduler(SupplySystemQueryService supplySystemQueryService) {
        this.supplySystemQueryService = supplySystemQueryService;
    }

    @Scheduled(fixedDelayString = "${scheduler.supply-check.interval}")
    public void checkSuppliesStatus() {
        log.info("Checking supplies status");


        try {
            List<Ingredient> suppliesToOrderList = supplySystemQueryService.getIngredientStockStatus().stream()
                    .filter(Ingredient::needsRestock)
                    .toList();
        } catch (Exception e) {
            log.error("Error while checking supplies status: {}" + e.getMessage());
            e.getMessage();
        }
//        supplySystem.checkSuppliesStatus();
    }

}
