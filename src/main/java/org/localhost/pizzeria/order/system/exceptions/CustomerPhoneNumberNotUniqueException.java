package org.localhost.pizzeria.order.system.exceptions;

import lombok.Getter;

@Getter
public class CustomerPhoneNumberNotUniqueException extends RuntimeException {
    private final int errorCode;

    public CustomerPhoneNumberNotUniqueException(OrderExceptionsMessages message) {
        super(message.getErrorMessage());
        this.errorCode = message.getErrorCode();
    }
}
