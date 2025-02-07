package org.localhost.pizzeria.order.system.customer.exceptions.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CustomerExceptionsMessages {
    CUSTOMER_EMAIL_NOT_UNIQUE(201, "Customer email is not unique!"),
    CUSTOMER_PHONE_NUMBER_NOT_UNIQUE(202, "Customer phone number is not unique!"),
    CUSTOMER_NOT_FOUND(200, "Customer not found!");

    private final int errorCode;
    private final String errorMessage;
}
