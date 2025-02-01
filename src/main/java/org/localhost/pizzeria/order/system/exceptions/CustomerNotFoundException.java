package org.localhost.pizzeria.order.system.exceptions;

import lombok.Getter;

@Getter
public class CustomerNotFoundException extends RuntimeException {

    private final int errorCode;

    public CustomerNotFoundException(OrderExceptionsMessages message) {
        super(message.getErrorMessage());
        this.errorCode = message.getErrorCode();

    }
}
