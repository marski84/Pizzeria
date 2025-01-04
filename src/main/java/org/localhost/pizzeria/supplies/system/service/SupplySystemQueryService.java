package org.localhost.pizzeria.supplies.system.service;

import org.localhost.pizzeria.supplies.system.model.Ingredient;

import java.util.List;

public interface SupplySystemQueryService {
    Ingredient isIngredientInStock (Ingredient ingredient);
    List<Ingredient> getIngredientStockStatus();

//    queue method
    List<OrderQueueItem> getProcessingQueueStatus();
    List<OrderQueueItem> getStockQueueStatus();


}
