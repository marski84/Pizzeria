package org.localhost.pizzeria.order.system.repository;

import org.localhost.pizzeria.order.system.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
