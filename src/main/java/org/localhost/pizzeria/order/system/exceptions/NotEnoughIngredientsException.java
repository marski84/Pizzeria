package org.localhost.pizzeria.order.system.exceptions;

import lombok.Getter;

@Getter
public class NotEnoughIngredientsException extends RuntimeException {
    private final int errorCode;
    public NotEnoughIngredientsException(OrderExceptionsMessages messages) {
        super(messages.getErrorMessage());
        this.errorCode = messages.getErrorCode();
    }
}
