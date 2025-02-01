package org.localhost.pizzeria.order.system.pizza.model;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
//@Value
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PizzaIngredient {
    Long ingredientId;
    Integer amount;


    public static PizzaIngredient create(Long ingredientId, Integer amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        return new PizzaIngredient(ingredientId, amount);
    }
}
