package org.localhost.pizzeria.order.system.service.impl;

import org.localhost.pizzeria.order.system.order.OrderStatus;
import org.localhost.pizzeria.order.system.order.exceptions.OrderAlreadyProcessedException;
import org.localhost.pizzeria.order.system.order.exceptions.OrderNotFoundException;
import org.localhost.pizzeria.order.system.order.exceptions.messages.OrderExceptionsMessages;
import org.localhost.pizzeria.order.system.order.model.Order;
import org.localhost.pizzeria.order.system.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryOrderRepository implements OrderRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryOrderRepository.class);
    Map<Long, Order> orders = new HashMap<>();
    AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Order save(Order order) {
        System.out.println("wtf");
        System.out.println(order.toString());
        if (order.getId() == null) {
            order.setId(idGenerator.getAndIncrement());
            order.setOrderStatus(OrderStatus.NEW);
            order.setOrderReceivedDate(ZonedDateTime.now());
        }
        orders.put(order.getId(), order);
        System.out.println("in memory order save " + order.getId());
        return orders.get(order.getId());
    }

    @Override
    public void deleteOrder(Long id) {
        System.out.println("in memory order delete " + orders.size());
        Order order = orders.values().stream().filter(o -> Objects.equals(o.getId(), id)).findFirst()
                .orElseThrow(
                () -> new OrderNotFoundException(OrderExceptionsMessages.ORDER_NOT_FOUND)
        );
        if (order.getOrderStatus() != OrderStatus.NEW) {
            throw new OrderAlreadyProcessedException(OrderExceptionsMessages.ORDER_ALREADY_PROCESSED);
        }
        orders.remove(id);
    }

    @Override
    public Order findById(Long id) {
        orders.values().stream().forEach(o -> System.out.println(o.getOrderStatus()));
        return orders.values().stream()
                .filter(o -> Objects.equals(o.getId(), id))
                .findFirst().orElseThrow(
                        () -> new OrderNotFoundException(OrderExceptionsMessages.ORDER_NOT_FOUND)
                );
    }
}
