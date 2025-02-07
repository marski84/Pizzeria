package org.localhost.pizzeria.order.system.order.repository;

import org.localhost.pizzeria.order.system.order.OrderStatus;
import org.localhost.pizzeria.order.system.order.exceptions.OrderAlreadyProcessedException;
import org.localhost.pizzeria.order.system.order.exceptions.OrderNotFoundException;
import org.localhost.pizzeria.order.system.order.exceptions.messages.OrderExceptionsMessages;
import org.localhost.pizzeria.order.system.order.model.Order;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderSqlRepository implements OrderRepository {
    private final OrderCrudRepository orderCrudRepository;

    public OrderSqlRepository(OrderCrudRepository orderCrudRepository) {
        this.orderCrudRepository = orderCrudRepository;
    }

    @Override
    public Order save(Order newOrder) {
        return orderCrudRepository.save(newOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderCrudRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException(OrderExceptionsMessages.ORDER_NOT_FOUND)
        );
        if (order.getOrderStatus() != OrderStatus.NEW) {
            throw new OrderAlreadyProcessedException(OrderExceptionsMessages.ORDER_ALREADY_PROCESSED);
        }
        orderCrudRepository.deleteById(id);
    }

    @Override
    public Order findById(Long id) {
        return orderCrudRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException(OrderExceptionsMessages.ORDER_NOT_FOUND)
        );
    }
}
