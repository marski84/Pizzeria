package org.localhost.pizzeria.supplies.system.exceptions.messages;

import lombok.Getter;

@Getter
public enum IngredientExceptionsDetails {
    INGREDIENT_NOT_FOUND(300, "Book with given ID does not exist");

    private final int code;
    private final String message;

    IngredientExceptionsDetails(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
