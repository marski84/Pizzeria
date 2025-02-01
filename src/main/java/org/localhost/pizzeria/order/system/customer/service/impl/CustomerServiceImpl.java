package org.localhost.pizzeria.order.system.customer.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.localhost.pizzeria.order.system.customer.dto.CustomerConflictDto;
import org.localhost.pizzeria.order.system.customer.dto.NewCustomerDto;
import org.localhost.pizzeria.order.system.customer.dto.UpdateCustomerDataDto;
import org.localhost.pizzeria.order.system.customer.exceptions.CustomerEmailNotUniqueException;
import org.localhost.pizzeria.order.system.customer.exceptions.CustomerNotFoundException;
import org.localhost.pizzeria.order.system.customer.exceptions.CustomerPhoneNumberNotUniqueException;
import org.localhost.pizzeria.order.system.customer.exceptions.messages.CustomerExceptionsMessages;
import org.localhost.pizzeria.order.system.customer.model.Customer;
import org.localhost.pizzeria.order.system.order.model.Order;
import org.localhost.pizzeria.order.system.customer.repository.CustomerRepository;
import org.localhost.pizzeria.order.system.customer.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new CustomerNotFoundException(CustomerExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    @Override
    public Customer findCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new CustomerNotFoundException(CustomerExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer registerNewCustomer(NewCustomerDto customer) {
        customerRepository.findByEmailOrPhoneNumber(customer.getEmail(), customer.getPhoneNumber()).ifPresent(foundCustomer -> validateCustomerUniqueness(foundCustomer, customer));

        Customer newCustomer = Customer.fromNewCustomerDto(customer);
        log.info("New customer registered with id: {}", newCustomer.getId());

        return customerRepository.save(newCustomer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long deleteCustomer(long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(CustomerExceptionsMessages.CUSTOMER_NOT_FOUND));
        customerRepository.delete(customer);
        return customer.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer updateCustomerData(UpdateCustomerDataDto updateCustomerDataDto) {
        Customer customer = customerRepository.findById(updateCustomerDataDto.getId())
                .orElseThrow(() -> new CustomerNotFoundException(CustomerExceptionsMessages.CUSTOMER_NOT_FOUND));

        CustomerConflictDto conflicts = customerRepository.checkCustomerDataConflicts(
                customer.getId(),
                updateCustomerDataDto.getEmail(),
                updateCustomerDataDto.getPhoneNumber()
        );

        if (conflicts.isEmailConflict()) {
            throw new CustomerEmailNotUniqueException(CustomerExceptionsMessages.CUSTOMER_EMAIL_NOT_UNIQUE);
        }
        if (conflicts.isPhoneNumberConflict()) {
            throw new CustomerPhoneNumberNotUniqueException(CustomerExceptionsMessages.CUSTOMER_PHONE_NUMBER_NOT_UNIQUE);
        }

        customer.setEmail(updateCustomerDataDto.getEmail());
        customer.setPhoneNumber(updateCustomerDataDto.getPhoneNumber());
        customer.setAddress(updateCustomerDataDto.getAddress());

        return customerRepository.save(customer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCustomerOrder(long customerId, Order order) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(CustomerExceptionsMessages.CUSTOMER_NOT_FOUND));
        customer.addOrder(order);
        customerRepository.save(customer);
    }

    @Override
    public List<Order> getOrdersByCustomerId(long customerId) {
        return customerRepository.findById(customerId).map(customer -> List.copyOf(customer.getOrders())).orElseThrow(() -> new CustomerNotFoundException(CustomerExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    @Override
    public List<Order> getOrdersByCustomerEmail(String customerEmail) {
        return customerRepository.findByEmail(customerEmail).map(customer -> List.copyOf(customer.getOrders())).orElseThrow(() -> new CustomerNotFoundException(CustomerExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    @Override
    public List<Order> getOrdersByCustomerPhoneNumber(String customerPhone) {
        return customerRepository.findByPhoneNumber(customerPhone).map(customer -> List.copyOf(customer.getOrders())).orElseThrow(() -> new CustomerNotFoundException(CustomerExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    private void validateCustomerUniqueness(Customer foundCustomer, NewCustomerDto newCustomer) {
        if (foundCustomer.getEmail().equals(newCustomer.getEmail())) {
            log.error("Not unique email for {}", newCustomer);
            throw new CustomerEmailNotUniqueException(CustomerExceptionsMessages.CUSTOMER_EMAIL_NOT_UNIQUE);
        }
        if (foundCustomer.getPhoneNumber().equals(newCustomer.getPhoneNumber())) {
            log.error("Not unique phone number for {}", newCustomer);
            throw new CustomerPhoneNumberNotUniqueException(CustomerExceptionsMessages.CUSTOMER_PHONE_NUMBER_NOT_UNIQUE);
        }
    }


}
