package org.localhost.pizzeria.order.system.service.impl;

import org.localhost.pizzeria.order.system.customer.dto.CustomerConflictDto;
import org.localhost.pizzeria.order.system.customer.exceptions.CustomerNotFoundException;
import org.localhost.pizzeria.order.system.customer.exceptions.messages.CustomerExceptionsMessages;
import org.localhost.pizzeria.order.system.customer.model.Customer;
import org.localhost.pizzeria.order.system.customer.repository.CustomerRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryCustomerCrudRepository implements CustomerRepository {
    private final Map<Long, Customer> customers = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);



    @Override
    public Customer findByEmail(String email) {
        return customers.values().stream().filter(customer -> customer.getEmail().equals(email))
                .findFirst()
                .orElseThrow(()-> new CustomerNotFoundException(CustomerExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    @Override
    public Customer findByPhoneNumber(String phone) {
        return customers.values().stream().filter(customer -> customer.getPhoneNumber().equals(phone))
                .findFirst()
                .orElseThrow(()-> new CustomerNotFoundException(CustomerExceptionsMessages.CUSTOMER_NOT_FOUND));
    }


    @Override
    public Customer findByEmailOrPhoneNumber(String email, String phone) {
        return customers.values().stream().filter(
                        customer -> customer.getEmail().equals(email)
                                || customer.getPhoneNumber().equals(phone))
                .findFirst()
                .orElseThrow(()-> new CustomerNotFoundException(CustomerExceptionsMessages.CUSTOMER_NOT_FOUND));
    }




    @Override
    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(idGenerator.getAndIncrement());
        }
        System.out.println("Saving customer: " + customer.getId());
        customers.put(customer.getId(), customer);
        return customers.get(customer.getId());
    }

    @Override
    public Customer findById(long customerId) {
        return customers.values().stream().filter(c -> c.getId() == customerId).findFirst()
                .orElseThrow(() -> new CustomerNotFoundException(CustomerExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public CustomerConflictDto checkCustomerDataConflicts(String email, String phoneNumber) {
        boolean emailExists = customers.values().stream().anyMatch(customer -> customer.getEmail().equals(email));
        boolean phoneNumberExists = customers.values().stream().anyMatch(customer -> customer.getPhoneNumber().equals(phoneNumber));
        return new CustomerConflictDto(emailExists, phoneNumberExists);
    }


}
