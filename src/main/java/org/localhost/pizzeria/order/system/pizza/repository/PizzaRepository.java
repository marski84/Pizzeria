package org.localhost.pizzeria.order.system.pizza.repository;

import org.localhost.pizzeria.order.system.pizza.model.Pizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends CrudRepository<Pizza, Long> {

}
