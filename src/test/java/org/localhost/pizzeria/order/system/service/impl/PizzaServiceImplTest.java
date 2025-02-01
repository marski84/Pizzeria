package org.localhost.pizzeria.order.system.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.localhost.pizzeria.order.system.pizza.dto.NewPizzaDto;
import org.localhost.pizzeria.order.system.order.exceptions.messages.OrderExceptionsMessages;
import org.localhost.pizzeria.order.system.pizza.exceptions.PizzaAlreadyAddedException;
import org.localhost.pizzeria.order.system.pizza.exceptions.PizzaNotFoundException;
import org.localhost.pizzeria.order.system.pizza.model.Pizza;
import org.localhost.pizzeria.order.system.pizza.model.PizzaIngredient;
import org.localhost.pizzeria.order.system.pizza.repository.PizzaRepository;
import org.localhost.pizzeria.order.system.pizza.service.PizzaService;
import org.localhost.pizzeria.order.system.pizza.service.impl.PizzaServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.localhost.pizzeria.order.system.service.impl.data.IngredientsTestData.*;


class PizzaServiceImplTest {

    private PizzaService objectUnderTest;
    private PizzaRepository inMemoryPizzaRepository;

    private NewPizzaDto testPizza = NewPizzaDto.builder()
            .name("marinara")
            .ingredients(
                    List.of(
                            new PizzaIngredient(flour.getId(), 100),
                            new PizzaIngredient(yeast.getId(), 20),
                            new PizzaIngredient(milk.getId(), 200),
                            new PizzaIngredient(mozzarella.getId(), 200)
                    )
            )
            .build();

    private NewPizzaDto secondTestPizza = NewPizzaDto.builder()
            .name("Pepperoni")
            .ingredients(
                    List.of(
                            new PizzaIngredient(flour.getId(), 100),
                            new PizzaIngredient(yeast.getId(), 20),
                            new PizzaIngredient(milk.getId(), 200),
                            new PizzaIngredient(mozzarella.getId(), 200),
                            new PizzaIngredient(pepperoni.getId(), 200)
                    )
            )
            .build();

    @BeforeEach
    void setUp() {
        inMemoryPizzaRepository = new InMemoryPizzaRepository();
        objectUnderTest = new PizzaServiceImpl(inMemoryPizzaRepository);
    }

    @DisplayName("addPizzaToMenu should successfully add pizza to menu")
    @Test
    void addPizzaToMenu() {
//        when
        Pizza testResult = objectUnderTest.addPizzaToMenu(testPizza);
//        then
        assertAll(
                () -> assertEquals(testPizza.getName(), testResult.getName()),
                () -> assertNotNull(testResult.getId()),
                () -> assertEquals(testPizza.getIngredients().size(), testResult.getIngredients().size()),
                () -> assertEquals(testPizza.getPrice(), testResult.getPrice())
                );
    }

    @DisplayName("addPizzaToMenu should throw when pizza not found")
    @Test
    void addPizzaToMenuWhenPizzaNotFound() {
//        given
        objectUnderTest.addPizzaToMenu(testPizza);
//        when, then
        PizzaAlreadyAddedException testResult = assertThrows(
                PizzaAlreadyAddedException.class,
                () -> objectUnderTest.addPizzaToMenu(testPizza));

        assertAll(
                () -> assertEquals(OrderExceptionsMessages.PIZZA_NAME_NOT_UNIQUE.getErrorMessage(), testResult.getMessage()),
                () -> assertEquals(OrderExceptionsMessages.PIZZA_NAME_NOT_UNIQUE.getErrorCode(), testResult.getErrorCode())
        );
    }

    @DisplayName("removePizzaFromMenu should successfully remove pizza from menu")
    @Test
    void removePizzaFromMenu() {
//        given
        Pizza marinara= objectUnderTest.addPizzaToMenu(testPizza);
        Pizza pepperoni = objectUnderTest.addPizzaToMenu(secondTestPizza);
        int amountOfPizzasInMenu = objectUnderTest.getAllPizzas().size();
//        when
        objectUnderTest.removePizzaFromMenu(marinara.getId());
//        then
        List<Pizza> pizzaMenuSizeAfterDelete = objectUnderTest.getAllPizzas();
        Pizza testPizza = pizzaMenuSizeAfterDelete.get(0);
        assertAll(
                () -> assertEquals(amountOfPizzasInMenu -1, pizzaMenuSizeAfterDelete.size()),
                () -> assertEquals(pepperoni.getId(), testPizza.getId()),
                () -> assertEquals(pepperoni.getName(), testPizza.getName()),
                () -> assertEquals(testPizza.getIngredients().size(), testPizza.getIngredients().size()),
                () -> assertEquals(testPizza.getPrice(), testPizza.getPrice())

        );
    }

    @DisplayName("removePizzaFromMenu should throw when no pizza found")
    @Test
    void removePizzaFromMenuWhenNoPizzaFound() {
        long NON_EXISTING_ID = 1111;
//        when, then
        PizzaNotFoundException testResult = assertThrows(
                PizzaNotFoundException.class,
                () -> objectUnderTest.removePizzaFromMenu(NON_EXISTING_ID)
        );
        assertAll(
                () -> assertEquals(OrderExceptionsMessages.PIZZA_NOT_FOUND.getErrorMessage(), testResult.getMessage()),
                () -> assertEquals(OrderExceptionsMessages.PIZZA_NOT_FOUND.getErrorCode(), testResult.getErrorCode())
        );
    }

    @DisplayName("getPizzaById should return pizza")
    @Test
    void getPizzaById() {
//        given
        Pizza pizza = objectUnderTest.addPizzaToMenu(testPizza);
        objectUnderTest.addPizzaToMenu(secondTestPizza);
//        when
        Pizza testResult = objectUnderTest.getPizzaById(pizza.getId());
//        then
        assertAll(
                () -> assertEquals(pizza.getId(), testResult.getId()),
                () -> assertEquals(pizza.getName(), testResult.getName()),
                () -> assertEquals(pizza.getIngredients().size(), testResult.getIngredients().size())
        );
    }

    @DisplayName("getPizzaById should throw when no pizza found")
    @Test
    void getPizzaByIdWhenNoPizzaFound() {
//        given
        long NON_EXISTING_ID = 1111;
//        when, then
        PizzaNotFoundException testResult = assertThrows(
                PizzaNotFoundException.class,
                () -> objectUnderTest.getPizzaById(NON_EXISTING_ID)
        );
        assertAll(
                () -> assertEquals(OrderExceptionsMessages.PIZZA_NOT_FOUND.getErrorMessage(), testResult.getMessage()),
                () -> assertEquals(OrderExceptionsMessages.PIZZA_NOT_FOUND.getErrorCode(), testResult.getErrorCode())
        );
    }

    @DisplayName("getAllPizzas should return list of pizzas")
    @Test
    void getAllPizzas() {
//        given
        objectUnderTest.addPizzaToMenu(testPizza);
        objectUnderTest.addPizzaToMenu(secondTestPizza);
        int amountOfPizzasInMenu = objectUnderTest.getAllPizzas().size();
//        when
        List<Pizza> testResult = objectUnderTest.getAllPizzas();
//        then
        assertEquals(amountOfPizzasInMenu, testResult.size());
    }
}