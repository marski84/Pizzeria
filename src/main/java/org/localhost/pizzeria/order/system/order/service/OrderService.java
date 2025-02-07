package org.localhost.pizzeria.order.system.order.service;

import org.localhost.pizzeria.order.system.customer.model.Customer;
import org.localhost.pizzeria.order.system.order.dto.NewOrderDto;
import org.localhost.pizzeria.order.system.order.model.Order;

public interface OrderService {
    Order createOrder(NewOrderDto orderDto);
    void deleteOrder(Long id);
}
