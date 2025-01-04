package org.localhost.pizzeria.supplies.system.service;

import org.localhost.pizzeria.supplies.system.dto.OrderQueueItem;

public interface OrderQueueService {
    void addToProcessingQueue(OrderQueueItem order);
    void addToStockQueue(OrderQueueItem order);
    void processNextOrder();
    void processNextStockOrder();
}
