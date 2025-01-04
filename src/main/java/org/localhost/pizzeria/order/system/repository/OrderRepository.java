package org.localhost.pizzeria.order.system.repository;

import org.localhost.pizzeria.order.system.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
