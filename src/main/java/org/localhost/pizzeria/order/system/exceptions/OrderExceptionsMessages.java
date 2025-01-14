package org.localhost.pizzeria.order.system.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderExceptionsMessages {
    CUSTOMER_EMAIL_NOT_UNIQUE(100, "Customer email is not unique!"),
    CUSTOMER_PHONE_NUMBER_NOT_UNIQUE(200, "Customer phone number is not unique!"),
    CUSTOMER_NOT_FOUND(300, "Customer not found!");

    private int errorCode;
    private String errorMessage;
}
