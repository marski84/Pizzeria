package org.localhost.pizzeria.order.system.pizza.exceptions;

import lombok.Getter;
import org.localhost.pizzeria.order.system.order.exceptions.messages.OrderExceptionsMessages;

@Getter
public class PizzaNotFoundException extends RuntimeException {
    private final int errorCode;

    public PizzaNotFoundException(OrderExceptionsMessages message) {
        super(message.getErrorMessage());
        errorCode = message.getErrorCode();
    }
}
