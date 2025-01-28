package org.localhost.pizzeria.order.system.repository;

import org.localhost.pizzeria.order.system.model.Customer;
import org.localhost.pizzeria.order.system.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhoneNumber(String phone);
    Optional<Customer> findByEmailOrPhoneNumber(String email, String phone);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phone);
}
