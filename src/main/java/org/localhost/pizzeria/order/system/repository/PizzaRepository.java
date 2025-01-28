package org.localhost.pizzeria.order.system.repository;

import org.localhost.pizzeria.order.system.model.Pizza;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends CrudRepository<Pizza, Long> {
    boolean existsByName(String name);
}
