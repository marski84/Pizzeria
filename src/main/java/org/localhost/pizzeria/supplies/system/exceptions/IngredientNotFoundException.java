package org.localhost.pizzeria.supplies.system.exceptions;

import lombok.Getter;
import org.localhost.pizzeria.supplies.system.exceptions.messages.IngredientExceptionsDetails;

@Getter
public class IngredientNotFoundException extends RuntimeException {
    private final IngredientExceptionsDetails details;

    public IngredientNotFoundException(IngredientExceptionsDetails ingredientExceptionsDetails) {
        this.details = ingredientExceptionsDetails;
    }
}
