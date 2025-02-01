package org.localhost.pizzeria.order.system.pizza.service;

import org.localhost.pizzeria.order.system.pizza.dto.NewPizzaDto;
import org.localhost.pizzeria.order.system.pizza.model.Pizza;

import java.util.List;

public interface PizzaService {
    Pizza addPizzaToMenu(NewPizzaDto newPizzaDto);
    void removePizzaFromMenu(long pizzaId);
    Pizza getPizzaById(long pizzaId);
    List<Pizza> getAllPizzas();
}
