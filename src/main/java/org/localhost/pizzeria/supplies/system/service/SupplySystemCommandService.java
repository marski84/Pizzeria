package org.localhost.pizzeria.supplies.system.service;

import org.localhost.pizzeria.supplies.system.dto.EditIngredientDto;
import org.localhost.pizzeria.supplies.system.dto.IngredientDTO;
import org.localhost.pizzeria.supplies.system.dto.NewIngredientDto;
import org.localhost.pizzeria.supplies.system.model.Ingredient;

import java.util.List;

public interface SupplySystemCommandService {
    void updateIngredientRestockStatus(long ingredientId);
    void decreaseIngredientStock(long ingredientId, int amount);
    void increaseIngredientStock(long ingredientId, int amount);
    IngredientDTO registerNewIngredient(NewIngredientDto ingredientData);
    IngredientDTO updateIngredientData(long ingredientId, EditIngredientDto ingredientData);
    void removeIngredient(long ingredientId);
    List<Ingredient> submitOrderToStockQueue(List<Long> ingredientIds);


}
