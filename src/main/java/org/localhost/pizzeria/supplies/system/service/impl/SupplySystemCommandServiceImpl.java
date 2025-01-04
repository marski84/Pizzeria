package org.localhost.pizzeria.supplies.system.service.impl;

import org.localhost.pizzeria.supplies.system.exceptions.IngredientNotFoundException;
import org.localhost.pizzeria.supplies.system.exceptions.messages.IngredientExceptionsDetails;
import org.localhost.pizzeria.supplies.system.model.Ingredient;
import org.localhost.pizzeria.supplies.system.repository.SupplySystemRepository;
import org.localhost.pizzeria.supplies.system.service.SupplySystemCommandService;

public class SupplySystemCommandServiceImpl implements SupplySystemCommandService {
    private final SupplySystemRepository systemRepository;

    public SupplySystemCommandServiceImpl(SupplySystemRepository systemRepository) {
        this.systemRepository = systemRepository;
    }

    @Override
    public void updateIngredientRestockStatus(long ingredientId) {
        Ingredient ingredient = systemRepository.findById(ingredientId)
                .stream()
                .findFirst()
                .orElseThrow(
                        () -> new IngredientNotFoundException(IngredientExceptionsDetails.INGREDIENT_NOT_FOUND)
                );
    }
}
