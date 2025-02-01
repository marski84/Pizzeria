package org.localhost.pizzeria.order.system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.localhost.pizzeria.order.system.OrderStatus;

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
