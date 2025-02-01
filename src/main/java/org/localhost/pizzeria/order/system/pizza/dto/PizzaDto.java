package org.localhost.pizzeria.order.system.pizza.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.localhost.pizzeria.order.system.pizza.model.Pizza;
import org.localhost.pizzeria.order.system.pizza.model.PizzaIngredient;

import java.util.List;

@Getter
@Setter
@Builder
public class PizzaDto {
    private String name;
    private List<PizzaIngredient> ingredients;

    public static PizzaDto fromPizza(Pizza pizza) {
        return PizzaDto.builder()
                .name(pizza.getName())
                .ingredients(pizza.getIngredients())
                .build();
    }
}
