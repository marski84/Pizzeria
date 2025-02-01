package org.localhost.pizzeria.order.system.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.localhost.pizzeria.order.system.order.OrderStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OrderDto {
    private long id;
    private OrderStatus orderStatus;
    private ZonedDateTime orderReceivedDate;
    private String name;
    private int amount;
}
