package org.localhost.pizzeria.order.system.service;

import org.localhost.pizzeria.order.system.dto.NewPizzaDto;
import org.localhost.pizzeria.order.system.model.Pizza;

import java.util.List;

public interface PizzaService {
    Pizza addPizzaToMenu(NewPizzaDto newPizzaDto);
    void removePizzaFromMenu(long pizzaId);
    Pizza getPizzaById(long pizzaId);
    List<Pizza> getAllPizzas();
}
