package org.localhost.pizzeria.order.system.service.impl;

import jakarta.annotation.PostConstruct;
import org.localhost.pizzeria.nats.publisher.Publisher;
import org.localhost.pizzeria.nats.subscriber.Subscriber;
import org.localhost.pizzeria.order.system.OrderStatus;
import org.localhost.pizzeria.order.system.dto.OrderDto;
import org.localhost.pizzeria.order.system.model.Order;
import org.localhost.pizzeria.order.system.repository.OrderRepository;
import org.localhost.pizzeria.order.system.service.OrderService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final SimpMessagingTemplate messagingTemplate;
    private final OrderRepository orderRepository;
    private final Subscriber subscriber;
    private final Publisher publisher;

    public OrderServiceImpl(SimpMessagingTemplate messagingTemplate, OrderRepository orderRepository, Subscriber subscribe, Publisher publisher) {
        this.messagingTemplate = messagingTemplate;
        this.orderRepository = orderRepository;
        this.subscriber = subscribe;
        this.publisher = publisher;
    }

    public Order createOrder(OrderDto orderDto) {
        Order order = new Order();
        // Zapisz zamówienie
//        Order order = orderRepository.save(mapToOrder(orderDto));

        // Opublikuj do NATS dla sprawdzenia składników
//        natsConnection.publish("check.ingredients",
//                objectMapper.writeValueAsBytes(order));
        publisher.publish(order, "order.new-order");

        return new Order();
    }

    // NATS Subscriber
    @PostConstruct
    public void subscribeToIngredientCheck() {
        subscriber.subscribe("ingredients.status");

    }
}
