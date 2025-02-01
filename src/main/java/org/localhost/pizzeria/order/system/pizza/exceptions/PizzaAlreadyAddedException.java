package org.localhost.pizzeria.order.system.pizza.exceptions;

import lombok.Getter;
import org.localhost.pizzeria.order.system.order.exceptions.messages.OrderExceptionsMessages;

@Getter
public class PizzaAlreadyAddedException extends RuntimeException {
    private final int errorCode;

    public PizzaAlreadyAddedException(OrderExceptionsMessages message) {
        super(message.getErrorMessage());
        this.errorCode = message.getErrorCode();
    }
}
