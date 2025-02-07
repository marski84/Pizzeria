package org.localhost.pizzeria.order.system.order.exceptions;

import org.localhost.pizzeria.order.system.order.exceptions.messages.OrderExceptionsMessages;

public class OrderAlreadyProcessedException extends RuntimeException {
    private final int errorCode;

    public OrderAlreadyProcessedException(OrderExceptionsMessages orderExceptionsMessages) {
        super(orderExceptionsMessages.getErrorMessage());
        this.errorCode = orderExceptionsMessages.getErrorCode();
    }
}
