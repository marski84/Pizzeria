package org.localhost.pizzeria.supplies.system.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.localhost.pizzeria.supplies.system.exceptions.IngredientNotFoundException;
import org.localhost.pizzeria.supplies.system.exceptions.messages.IngredientExceptionsDetails;
import org.localhost.pizzeria.supplies.system.model.Ingredient;
import org.localhost.pizzeria.supplies.system.repository.SupplySystemRepository;
import org.localhost.pizzeria.supplies.system.service.SupplySystemQueryService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupplySystemQueryServiceImplTest {

    private SupplySystemQueryService objectUnderTest;
    private SupplySystemRepository ingredientRepository;

    @BeforeEach
    void setUp() {
        ingredientRepository = new InMemoryIngredientRepository();
        objectUnderTest = new SupplySystemQueryServiceImpl(ingredientRepository);
    }

    private Ingredient milk = Ingredient.builder()
            .productName("Milk")
            .amountInStock(100)
            .minimumRequiredAmount(10)
            .unitType(Ingredient.UnitType.LITERS)
            .build();

    private Ingredient bread = Ingredient.builder()
            .productName("Bread")
            .amountInStock(200)
            .minimumRequiredAmount(200)
            .unitType(Ingredient.UnitType.PACKAGES)
            .build();


    @Test
    @DisplayName("isIngredientInStock should return true if requested quantity of ingredient < amount in stock")
    void isIngredientInStock() {
//        given
        long TEST_INGREDIENT_AMOUNT = 2;
        Ingredient testIngredient = ingredientRepository.save(milk);
//        when
        boolean testResult = objectUnderTest.isIngredientInStock(testIngredient.getId(), TEST_INGREDIENT_AMOUNT);
//        then
        assertTrue(testResult);
    }

    @Test
    @DisplayName("isIngredientInStock should return false if requested quantity of ingredient > amount in stock")
    void isIngredientInStockShouldReturnFalse() {
//        given
        long TEST_INGREDIENT_AMOUNT = 200;
        Ingredient testIngredient = ingredientRepository.save(milk);
//        when
        boolean testResult = objectUnderTest.isIngredientInStock(testIngredient.getId(), TEST_INGREDIENT_AMOUNT);
//        then
        assertFalse(testResult);
    }

    @Test
    @DisplayName("isIngredientInStock should throw when ingredient not found")
    void isIngredientInStockWhenIngredientNotFound() {
        long TEST_INGREDIENT_AMOUNT = 2;
        long NON_EXISTING_INGREDIENT_ID = 100;
//        when, then
        IngredientNotFoundException testResult = assertThrows(
                IngredientNotFoundException.class,
                () -> objectUnderTest.isIngredientInStock(NON_EXISTING_INGREDIENT_ID, TEST_INGREDIENT_AMOUNT));

        assertAll(
                () -> assertEquals(
                        IngredientExceptionsDetails.INGREDIENT_NOT_FOUND.getCode(),
                        testResult.getDetails().getCode()
                ),
                () -> assertEquals(
                        IngredientExceptionsDetails.INGREDIENT_NOT_FOUND.getMessage(),
                        testResult.getDetails().getMessage()
                )
        );


    }

    @Test
    @DisplayName("getIngredientsStockStatus should return list of ingredients with stock amount")
    void getIngredientsStockStatus() {
//        given
        ingredientRepository.save(milk);
        ingredientRepository.save(bread);
        final int AMOUNT_OF_INGREDIENTS_IN_STOCK = 2;
//        when
        List<Ingredient> testResult = objectUnderTest.getIngredientsStockStatus();
//        then
        assertAll(
                () -> assertEquals(AMOUNT_OF_INGREDIENTS_IN_STOCK, testResult.size())
        );

        List<Ingredient> suppliesToOrderList = objectUnderTest.getIngredientsStockStatus().stream()
                .filter(ingredient -> ingredient.getAmountInStock() <= ingredient.getMinimumRequiredAmount())
                .toList();

        System.out.println(suppliesToOrderList.stream().map(Ingredient::getProductName));
    }


}