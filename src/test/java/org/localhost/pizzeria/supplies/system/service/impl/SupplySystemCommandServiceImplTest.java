package org.localhost.pizzeria.supplies.system.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.localhost.pizzeria.supplies.system.dto.EditIngredientDto;
import org.localhost.pizzeria.supplies.system.dto.IngredientDTO;
import org.localhost.pizzeria.supplies.system.dto.NewIngredientDto;
import org.localhost.pizzeria.supplies.system.exceptions.IngredientNotFoundException;
import org.localhost.pizzeria.supplies.system.exceptions.messages.IngredientExceptionsDetails;
import org.localhost.pizzeria.supplies.system.model.Ingredient;
import org.localhost.pizzeria.supplies.system.service.SupplySystemCommandService;

import static org.junit.jupiter.api.Assertions.*;


class SupplySystemCommandServiceImplTest {

    private SupplySystemCommandService objectUnderTest;
    private InMemoryIngredientRepository ingredientRepository;
    private final long NON_EXISTING_INGREDIENT_ID = 1000;

    @BeforeEach
    void setUp() {
        ingredientRepository = new InMemoryIngredientRepository();
        objectUnderTest = new SupplySystemCommandServiceImpl(ingredientRepository);
    }

    private final Ingredient milk = Ingredient.builder()
            .productName("Milk")
            .amountInStock(100)
            .minimumRequiredAmount(10)
            .unitType(Ingredient.UnitType.LITERS)
            .build();

    private final Ingredient bread = Ingredient.builder()
            .productName("Bread")
            .amountInStock(200)
            .minimumRequiredAmount(200)
            .unitType(Ingredient.UnitType.PACKAGES)
            .build();

    @Test
    @DisplayName("decreaseIngredientStock should decrease amount of ingredient")
    void decreaseIngredientStock() {
//        given
        Ingredient testIngredient = ingredientRepository.save(milk);
        int AMOUNT_TO_DECREASE = 10;
        int TEST_RESULT_AMOUNT = testIngredient.getAmountInStock() - AMOUNT_TO_DECREASE;
//        when
        objectUnderTest.decreaseIngredientStock(testIngredient.getId(), AMOUNT_TO_DECREASE);
//        then
        assertEquals(
                ingredientRepository.findById(testIngredient.getId()).get().getAmountInStock(),
                TEST_RESULT_AMOUNT);
    }

    @Test
    @DisplayName("decreaseIngredientStock should throw when no ingredient is found")
    void decreaseIngredientStockWhenNoIngredientFound() {
//        when, then
        IngredientNotFoundException testResult = assertThrows(
                IngredientNotFoundException.class,
                () -> objectUnderTest.decreaseIngredientStock(NON_EXISTING_INGREDIENT_ID, 100)
        );
        assertAll(
                () -> assertEquals(
                        IngredientExceptionsDetails.INGREDIENT_NOT_FOUND.getCode(),
                        testResult.getDetails().getCode()),
                () -> assertEquals(
                        IngredientExceptionsDetails.INGREDIENT_NOT_FOUND.getMessage(),
                        testResult.getDetails().getMessage())
        );
    }

    @Test
    @DisplayName("increaseIngredientStock should increase ingredient amount")
    void increaseIngredientStock() {
//        given
        Ingredient testIngredient = ingredientRepository.save(bread);
        int AMOUNT_TO_INCREASE = 100;
        int EXPECTED_AMOUNT = testIngredient.getAmountInStock() + AMOUNT_TO_INCREASE;
//        when
        objectUnderTest.increaseIngredientStock(testIngredient.getId(), AMOUNT_TO_INCREASE);
//        then
        assertEquals(ingredientRepository.findById(testIngredient.getId()).get().getAmountInStock(), EXPECTED_AMOUNT);
    }

    @Test
    @DisplayName("increaseIngredientStock should throw when no ingredient found")
    void increaseIngredientStockWhenNoIngredientFound() {
        IngredientNotFoundException testResult = assertThrows(
                IngredientNotFoundException.class,
                () -> objectUnderTest.increaseIngredientStock(NON_EXISTING_INGREDIENT_ID, 100)
        );
        assertAll(
                () -> assertEquals(
                        IngredientExceptionsDetails.INGREDIENT_NOT_FOUND.getCode(),
                        testResult.getDetails().getCode()),
                () -> assertEquals(
                        IngredientExceptionsDetails.INGREDIENT_NOT_FOUND.getMessage(),
                        testResult.getDetails().getMessage())
        );
    }

    @Test
    @DisplayName("registerNewIngredient should register new ingredient")
    void registerNewIngredient() {
//        given
        NewIngredientDto newIngredientDto = NewIngredientDto
                .builder()
                .productName("test ingredient")
                .unitType(Ingredient.UnitType.PACKAGES)
                .minimumRequiredAmount(50)
                .amountInStock(200)
                .build();
//        when
        IngredientDTO testResult = objectUnderTest.registerNewIngredient(newIngredientDto);
//        then
        assertAll(
                () -> assertEquals(newIngredientDto.getProductName(), testResult.getProductName()),
                () -> assertEquals(newIngredientDto.getUnitType(), testResult.getUnitType()),
                () -> assertEquals(newIngredientDto.getAmountInStock(), testResult.getAmountInStock()),
                () -> assertEquals(newIngredientDto.getMinimumRequiredAmount(), testResult.getMinimumRequiredAmount())
        );
    }

    @Test
    @DisplayName("registerNewIngredient should throw when not valid dto passed")
    void registerNewIngredientWhenNotValidDto() {
//        when, then
        Exception testResult = assertThrows(
                IllegalArgumentException.class,
                () -> objectUnderTest.registerNewIngredient(null)
        );
    }

    @Test
    @DisplayName("updateIngredientData should update ingredient data")
    void updateIngredientData() {
//        given
        Ingredient testIngredient = ingredientRepository.save(milk);
        EditIngredientDto editIngredientDto = EditIngredientDto.builder()
                .productName("updated milk name")
                .minimumRequiredAmount(200)
                .build();
//        when
        IngredientDTO testResult = objectUnderTest.updateIngredientData(testIngredient.getId(), editIngredientDto);
//        then
        assertAll(
                ()-> assertEquals(testIngredient.getId(), testResult.getId()),
                ()-> assertEquals(testIngredient.getProductName(), testResult.getProductName()),
                ()-> assertEquals(testIngredient.getMinimumRequiredAmount(), testResult.getMinimumRequiredAmount())
        );
    }

    @Test
    @DisplayName("updateIngredientData should throw when trying to update non existing ingredient")
    void updateIngredientDataThrowsWhenNoIngredientFound() {
//        given
        EditIngredientDto editIngredientDto = EditIngredientDto.builder()
                .productName("updated milk name")
                .minimumRequiredAmount(200)
                .build();
//        when, then
        IngredientNotFoundException testResult = assertThrows(
                IngredientNotFoundException.class,
                () -> objectUnderTest.updateIngredientData(NON_EXISTING_INGREDIENT_ID, editIngredientDto)
        );
        assertAll(
                () -> assertEquals(
                        IngredientExceptionsDetails.INGREDIENT_NOT_FOUND.getCode(),
                        testResult.getDetails().getCode()),
                () -> assertEquals(
                        IngredientExceptionsDetails.INGREDIENT_NOT_FOUND.getMessage(),
                        testResult.getDetails().getMessage())
        );
    }

    @Test
    @DisplayName("updateIngredientData should throw when trying to update non existing ingredient")
    void updateIngredientDataThrowsWhenNotValidDto() {
//        given
        Ingredient testIngredient= ingredientRepository.save(bread);
//        when, then
        Exception testResult = assertThrows(
                IllegalArgumentException.class,
                () -> objectUnderTest.updateIngredientData(testIngredient.getId(), null)
        );
    }

    @Test
    @DisplayName("remove ingredient should remove ingredient")
    void removeIngredient() {
//        given
        Ingredient testIngredient = ingredientRepository.save(bread);
        int amountOfIngredients = ingredientRepository.findAll().size();
//        when
        objectUnderTest.removeIngredient(testIngredient.getId());
//        then
        int amountOfIngredientsAfterRemove = ingredientRepository.findAll().size();
        assertNotEquals(amountOfIngredients, amountOfIngredientsAfterRemove);
    }

    @Test
    @DisplayName("removeIngredient should throw when no ingredient was found")
    void removeIngredientWhenNoIngredientFound() {
//        when, then
        IngredientNotFoundException testResult = assertThrows(
                IngredientNotFoundException.class,
                () -> objectUnderTest.removeIngredient(NON_EXISTING_INGREDIENT_ID)
        );
        assertAll(
                () -> assertEquals(
                        IngredientExceptionsDetails.INGREDIENT_NOT_FOUND.getCode(),
                        testResult.getDetails().getCode()),
                () -> assertEquals(
                        IngredientExceptionsDetails.INGREDIENT_NOT_FOUND.getMessage(),
                        testResult.getDetails().getMessage())
        );
    }

    @Test
    @DisplayName("remove ingredient should throw when ingredient id is negative")
    void removeIngredientThrowsWhenIngredientIdIsNegative() {
        //        when, then
       assertThrows(
                IllegalArgumentException.class,
                () -> objectUnderTest.removeIngredient(-1)
        );
    }

    @Test
    void submitOrderToStockQueue() {
    }
}