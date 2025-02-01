package org.localhost.pizzeria.order.system.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderExceptionsMessages {
    CUSTOMER_EMAIL_NOT_UNIQUE(100, "Customer email is not unique!"),
    CUSTOMER_PHONE_NUMBER_NOT_UNIQUE(200, "Customer phone number is not unique!"),
    CUSTOMER_NOT_FOUND(300, "Customer not found!"),
    PIZZA_NAME_NOT_UNIQUE(400, "Not unique pizza name!"),
    PIZZA_NOT_FOUND(500, "Pizza not found"),
    NOT_ENOUGH_INGREDIENTS(600, "Not enough ingridients in supply to complete order!");

    private int errorCode;
    private String errorMessage;
}
