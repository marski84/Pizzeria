package org.localhost.pizzeria.order.system.order.repository;
import org.localhost.pizzeria.order.system.order.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
