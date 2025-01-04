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
            .amountInStock(100l)
            .minimumRequiredAmount(10l)
            .unitType(Ingredient.UnitType.LITERS)
            .build();

    private Ingredient bread = Ingredient.builder()
            .productName("Bread")
            .amountInStock(200l)
            .minimumRequiredAmount(200l)
            .unitType(Ingredient.UnitType.PACKAGES)
            .build();


    @Test
    @DisplayName("isIngredientInStock should return Ingredient if it is in stock")
    void isIngredientInStock() {
//        given
        ingredientRepository.save(milk);
//        when
        Ingredient testResult = objectUnderTest.isIngredientInStock(milk);
        System.out.println(testResult.getProductName());
//        then
        assertAll(
                () -> assertEquals(milk.getProductName(), testResult.getProductName()),
                () -> assertEquals(milk.getAmountInStock(), testResult.getAmountInStock()),
                () -> assertEquals(milk.getMinimumRequiredAmount(), testResult.getMinimumRequiredAmount()),
                () -> assertEquals(milk.getUnitType(), testResult.getUnitType())
        );
    }

    @Test
    @DisplayName("isIngredientInStock should throw when ingredient not found")
    void isIngredientInStockWhenIngredientNotFound() {
//        when, then
        IngredientNotFoundException testResult = assertThrows(
                IngredientNotFoundException.class,
                () -> objectUnderTest.isIngredientInStock(milk));

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
    @DisplayName("getIngredientStockStatus should return list of ingredients with stock amount")
    void getIngredientStockStatus() {
//        given
        ingredientRepository.save(milk);
        ingredientRepository.save(bread);
//        when
        List<Ingredient> testResult = objectUnderTest.getIngredientStockStatus();
//        then
        assertAll(
                () -> assertEquals(2, testResult.size())
        );

        List<Ingredient> suppliesToOrderList = objectUnderTest.getIngredientStockStatus().stream()
                .filter(ingredient -> ingredient.getAmountInStock() <= ingredient.getMinimumRequiredAmount())
                .toList();

        System.out.println(suppliesToOrderList.stream().map(Ingredient::getProductName));

    }

}