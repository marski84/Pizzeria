package org.localhost.pizzeria.order.system.operator.repository;

import org.localhost.pizzeria.order.system.order.dto.NewOrderDto;
import org.localhost.pizzeria.order.system.order.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorCommandRepository {
    Order registerNewOrder(NewOrderDto newOrderDto);
}
