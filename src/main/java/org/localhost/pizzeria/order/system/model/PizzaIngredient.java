package org.localhost.pizzeria.order.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.localhost.pizzeria.supplies.system.model.Ingredient;
import org.springframework.beans.factory.annotation.Value;

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
