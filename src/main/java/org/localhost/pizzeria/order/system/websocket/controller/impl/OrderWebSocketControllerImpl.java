package org.localhost.pizzeria.order.system.websocket.controller.impl;

import org.localhost.pizzeria.order.system.order.dto.OrderDto;
import org.localhost.pizzeria.order.system.websocket.controller.OrderWebSocketController;
import org.localhost.pizzeria.order.system.websocket.dto.OrderStatusUpdateDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;

public class OrderWebSocketControllerImpl implements OrderWebSocketController {

    @Override
    @MessageMapping("/orders")
    @SendTo("/topic/orders")
    public OrderStatusUpdateDto handleOrderUpdate(OrderDto orderDto) {
        return new OrderStatusUpdateDto();
    }
}
