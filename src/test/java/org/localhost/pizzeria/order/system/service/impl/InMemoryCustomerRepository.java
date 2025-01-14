package org.localhost.pizzeria.order.system.service.impl;

import org.localhost.pizzeria.order.system.model.Customer;
import org.localhost.pizzeria.order.system.repository.CustomerRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryCustomerRepository implements CustomerRepository {
    private final Map<Long, Customer> customers = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customers.values().stream().filter(customer -> customer.getEmail().equals(email)).findFirst();
    }

    @Override
    public Optional<Customer> findByPhone(String phone) {
        return customers.values().stream().filter(customer -> customer.getPhoneNumber().equals(phone)).findFirst();
    }


    @Override
    public Optional<Customer> findByEmailOrPhone(String email, String phone) {
        return customers.values().stream().filter(
                        customer -> customer.getEmail().equals(email)
                                || customer.getPhoneNumber().equals(phone))
                .findFirst();
    }

    @Override
    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(idGenerator.getAndIncrement());
        }
        customers.put(customer.getId(), customer);
        return customers.get(customer.getId());
    }

    @Override
    public <S extends Customer> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Customer> findById(Long aLong) {
        return customers.values().stream().filter(customer -> customer.getId().equals(aLong)).findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Customer> findAll() {
        return null;
    }

    @Override
    public Iterable<Customer> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long customerId) {
        customers.remove(customerId);
    }

    @Override
    public void delete(Customer entity) {
        customers.remove(entity.getId());

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Customer> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
