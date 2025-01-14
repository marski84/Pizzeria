package org.localhost.pizzeria.order.system.exceptions;

import lombok.Getter;

@Getter
public class CustomerEmailNotUniqueException extends RuntimeException {
    private final int errorCode;

    public CustomerEmailNotUniqueException(OrderExceptionsMessages message) {
        super(message.getErrorMessage());
        this.errorCode = message.getErrorCode();
    }
}
