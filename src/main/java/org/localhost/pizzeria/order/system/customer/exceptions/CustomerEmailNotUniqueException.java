package org.localhost.pizzeria.order.system.customer.exceptions;

import lombok.Getter;
import org.localhost.pizzeria.order.system.customer.exceptions.messages.CustomerExceptionsMessages;

@Getter
public class CustomerEmailNotUniqueException extends RuntimeException {
    private final int errorCode;

    public CustomerEmailNotUniqueException(CustomerExceptionsMessages message) {
        super(message.getErrorMessage());
        this.errorCode = message.getErrorCode();
    }
}
