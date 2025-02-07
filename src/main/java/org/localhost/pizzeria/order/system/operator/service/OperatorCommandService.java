package org.localhost.pizzeria.order.system.operator.service;

import org.localhost.pizzeria.order.system.order.dto.NewOrderDto;
import org.localhost.pizzeria.order.system.order.model.Order;

public interface OperatorCommandService {
    Order registerNewOrder(NewOrderDto order);
    void deleteOrder(Long id);
    Order updateOrder(Order order);
}
