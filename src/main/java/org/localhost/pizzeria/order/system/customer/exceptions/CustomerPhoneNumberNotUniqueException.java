package org.localhost.pizzeria.order.system.customer.exceptions;

import lombok.Getter;
import org.localhost.pizzeria.order.system.customer.exceptions.messages.CustomerExceptionsMessages;

@Getter
public class CustomerPhoneNumberNotUniqueException extends RuntimeException {
    private final int errorCode;

    public CustomerPhoneNumberNotUniqueException(CustomerExceptionsMessages message) {
        super(message.getErrorMessage());
        this.errorCode = message.getErrorCode();
    }
}
