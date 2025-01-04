package org.localhost.pizzeria.supplies.system.service.impl;

import org.localhost.pizzeria.supplies.system.exceptions.IngredientNotFoundException;
import org.localhost.pizzeria.supplies.system.exceptions.messages.IngredientExceptionsDetails;
import org.localhost.pizzeria.supplies.system.model.Ingredient;
import org.localhost.pizzeria.supplies.system.repository.SupplySystemRepository;
import org.localhost.pizzeria.supplies.system.service.SupplySystemQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class SupplySystemQueryServiceImpl implements SupplySystemQueryService {
    private final SupplySystemRepository supplySystemRepository;

    public SupplySystemQueryServiceImpl(SupplySystemRepository supplySystemRepository) {
        this.supplySystemRepository = supplySystemRepository;
    }

    @Override
    public Ingredient isIngredientInStock(Ingredient ingredient) {
        return supplySystemRepository.findById(ingredient.getId()).orElseThrow(
                () -> new IngredientNotFoundException(IngredientExceptionsDetails.INGREDIENT_NOT_FOUND)
        );
    }

    @Override
    public List<Ingredient> getIngredientStockStatus() {
        return StreamSupport.stream(supplySystemRepository.findAll().spliterator(), false)
                .toList();
    }
}
