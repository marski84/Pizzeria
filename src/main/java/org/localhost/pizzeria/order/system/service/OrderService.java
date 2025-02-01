package org.localhost.pizzeria.order.system.service;

import org.localhost.pizzeria.order.system.dto.NewOrderDto;
import org.localhost.pizzeria.order.system.model.Order;

public interface OrderService {
    Order createOrder(NewOrderDto orderDto);


}
