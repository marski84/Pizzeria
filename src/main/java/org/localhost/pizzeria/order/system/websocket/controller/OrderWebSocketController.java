package org.localhost.pizzeria.order.system.websocket.controller;

import org.localhost.pizzeria.order.system.dto.OrderDto;
import org.localhost.pizzeria.order.system.websocket.dto.OrderStatusUpdateDto;

public interface OrderWebSocketController {
    OrderStatusUpdateDto handleOrderUpdate(OrderDto orderDto);
}
