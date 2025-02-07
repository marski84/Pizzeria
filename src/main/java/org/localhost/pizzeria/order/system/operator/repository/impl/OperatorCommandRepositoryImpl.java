package org.localhost.pizzeria.order.system.operator.repository.impl;

import org.localhost.pizzeria.order.system.customer.repository.CustomerRepository;
import org.localhost.pizzeria.order.system.operator.repository.OperatorCommandRepository;
import org.localhost.pizzeria.order.system.order.dto.NewOrderDto;
import org.localhost.pizzeria.order.system.order.model.Order;
import org.localhost.pizzeria.order.system.order.repository.OrderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OperatorCommandRepositoryImpl implements OperatorCommandRepository {
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public OperatorCommandRepositoryImpl(CustomerRepository customerRepository, OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order registerNewOrder(NewOrderDto newOrderDto) {
        return null;
    }
}
