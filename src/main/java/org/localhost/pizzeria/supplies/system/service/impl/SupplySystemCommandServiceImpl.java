package org.localhost.pizzeria.supplies.system.service.impl;

import org.localhost.pizzeria.supplies.system.dto.EditIngredientDto;
import org.localhost.pizzeria.supplies.system.dto.IngredientDTO;
import org.localhost.pizzeria.supplies.system.dto.NewIngredientDto;
import org.localhost.pizzeria.supplies.system.exceptions.IngredientNotFoundException;
import org.localhost.pizzeria.supplies.system.exceptions.messages.IngredientExceptionsDetails;
import org.localhost.pizzeria.supplies.system.model.Ingredient;
import org.localhost.pizzeria.supplies.system.repository.SupplySystemRepository;
import org.localhost.pizzeria.supplies.system.service.SupplySystemCommandService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

public class SupplySystemCommandServiceImpl implements SupplySystemCommandService {
    private final SupplySystemRepository supplySystemRepository;

    public SupplySystemCommandServiceImpl(SupplySystemRepository supplySystemRepository) {
        this.supplySystemRepository = supplySystemRepository;
    }

    @Override
    public void updateIngredientRestockStatus(long ingredientId) {
        Ingredient ingredient = supplySystemRepository.findById(ingredientId)
                .stream()
                .findFirst()
                .orElseThrow(
                        () -> new IngredientNotFoundException(IngredientExceptionsDetails.INGREDIENT_NOT_FOUND)
                );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decreaseIngredientStock(long ingredientId, int amount) {
        Ingredient ingredient = getIngredient(ingredientId);
        ingredient.setAmountInStock(ingredient.getAmountInStock() - amount);
        supplySystemRepository.save(ingredient);
    }

    @Override
    public void increaseIngredientStock(long ingredientId, int amount) {
        Ingredient ingredient = getIngredient(ingredientId);
        ingredient.setAmountInStock(ingredient.getAmountInStock() + amount);
        supplySystemRepository.save(ingredient);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IngredientDTO registerNewIngredient(NewIngredientDto ingredientData) {
        if (Objects.isNull(ingredientData)) {
            throw new IllegalArgumentException("Ingredient data is null");
        }
        Ingredient ingredient = Ingredient.builder()
                .productName(ingredientData.getProductName())
                .unitType(ingredientData.getUnitType())
                .minimumRequiredAmount(ingredientData.getMinimumRequiredAmount())
                .amountInStock(ingredientData.getAmountInStock())
                .build();
        Ingredient savedIngredient = supplySystemRepository.save(ingredient);

        return IngredientDTO.fromIngredient(savedIngredient);
    }

    @Override
    public IngredientDTO updateIngredientData(long ingredientId, EditIngredientDto ingredientData) {
        if (Objects.isNull(ingredientData)) {
            throw new IllegalArgumentException("Ingredient data is null");
        }
        Ingredient ingredient = getIngredient(ingredientId);
        ingredient.setProductName(ingredientData.getProductName());
        ingredient.setMinimumRequiredAmount(ingredientData.getMinimumRequiredAmount());

        Ingredient updatedIngredient = supplySystemRepository.save(ingredient);
        return IngredientDTO.fromIngredient(updatedIngredient);
    }

    @Override
    public void removeIngredient(long ingredientId) {
        if (ingredientId <= 0) {
            throw new IllegalArgumentException("Ingredient id must be greater than 0");
        }
        Ingredient ingredient = getIngredient(ingredientId);
        supplySystemRepository.delete(ingredient);
    }

    @Override
    public List<Ingredient> submitOrderToStockQueue(List<Long> ingredientIds) {
        return List.of();
    }

    private Ingredient getIngredient(long ingredientId) {
        return supplySystemRepository.findById(ingredientId).orElseThrow(
                () -> new IngredientNotFoundException(IngredientExceptionsDetails.INGREDIENT_NOT_FOUND)
        );
    }
}
