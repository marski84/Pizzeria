package org.localhost.pizzeria.order.system.order.exceptions.messages;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderExceptionsMessages {
    ORDER_NOT_FOUND(100, "Order not found"),
    ORDER_ALREADY_PROCESSED(101, "Order already processed"),
    PIZZA_NAME_NOT_UNIQUE(400, "Not unique pizza name!"),
    PIZZA_NOT_FOUND(500, "Pizza not found"),
    NOT_ENOUGH_INGREDIENTS(600, "Not enough ingridients in supply to complete order!");

    private final int errorCode;
    private final String errorMessage;
}
