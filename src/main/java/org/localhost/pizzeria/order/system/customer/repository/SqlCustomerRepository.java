package org.localhost.pizzeria.order.system.customer.repository;

import org.localhost.pizzeria.order.system.customer.dto.CustomerConflictDto;
import org.localhost.pizzeria.order.system.customer.exceptions.CustomerNotFoundException;
import org.localhost.pizzeria.order.system.customer.exceptions.messages.CustomerExceptionsMessages;
import org.localhost.pizzeria.order.system.customer.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class SqlCustomerRepository implements CustomerRepository {
    private final CustomerCrudRepository customerCrudRepository;

    public SqlCustomerRepository(CustomerCrudRepository customerCrudRepository) {
        this.customerCrudRepository = customerCrudRepository;
    }

    @Override
    public Customer findByEmail(String email) {
        return customerCrudRepository.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    @Override
    public Customer findByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public Customer findByEmailOrPhoneNumber(String email, String phoneNumber) {
        return customerCrudRepository.findByEmailOrPhoneNumber(email, phoneNumber)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    @Override
    public Customer save(Customer newCustomer) {
        return null;
    }

    @Override
    public Customer findById(long customerId) {
        return customerCrudRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(CustomerExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    @Override
    public void delete(Customer customer) {

    }


    public CustomerConflictDto checkCustomerDataConflicts(String email, String phoneNumber) {
        return customerCrudRepository.checkCustomerDataConflicts(email, phoneNumber);
    }
}
