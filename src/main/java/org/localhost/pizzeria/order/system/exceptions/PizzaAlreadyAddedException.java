package org.localhost.pizzeria.order.system.exceptions;

import lombok.Getter;

@Getter
public class PizzaAlreadyAddedException extends RuntimeException {
    private final int errorCode;

    public PizzaAlreadyAddedException(OrderExceptionsMessages message) {
        super(message.getErrorMessage());
        this.errorCode = message.getErrorCode();
    }
}
