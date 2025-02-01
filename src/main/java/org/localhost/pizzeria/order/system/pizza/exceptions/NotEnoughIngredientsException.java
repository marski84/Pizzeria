package org.localhost.pizzeria.order.system.pizza.exceptions;

import lombok.Getter;
import org.localhost.pizzeria.order.system.order.exceptions.messages.OrderExceptionsMessages;

@Getter
public class NotEnoughIngredientsException extends RuntimeException {
    private final int errorCode;
    public NotEnoughIngredientsException(OrderExceptionsMessages messages) {
        super(messages.getErrorMessage());
        this.errorCode = messages.getErrorCode();
    }
}
