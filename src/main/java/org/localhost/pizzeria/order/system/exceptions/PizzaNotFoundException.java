package org.localhost.pizzeria.order.system.exceptions;

import lombok.Getter;

@Getter
public class PizzaNotFoundException extends RuntimeException {
    private final int errorCode;

    public PizzaNotFoundException(OrderExceptionsMessages message) {
        super(message.getErrorMessage());
        errorCode = message.getErrorCode();
    }
}
