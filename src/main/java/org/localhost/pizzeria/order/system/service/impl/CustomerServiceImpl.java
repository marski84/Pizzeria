package org.localhost.pizzeria.order.system.service.impl;

import org.localhost.pizzeria.order.system.dto.NewCustomerDto;
import org.localhost.pizzeria.order.system.dto.UpdateCustomerDataDto;
import org.localhost.pizzeria.order.system.exceptions.CustomerEmailNotUniqueException;
import org.localhost.pizzeria.order.system.exceptions.CustomerNotFoundException;
import org.localhost.pizzeria.order.system.exceptions.CustomerPhoneNumberNotUniqueException;
import org.localhost.pizzeria.order.system.exceptions.OrderExceptionsMessages;
import org.localhost.pizzeria.order.system.model.Customer;
import org.localhost.pizzeria.order.system.model.Order;
import org.localhost.pizzeria.order.system.repository.CustomerRepository;
import org.localhost.pizzeria.order.system.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        return customerRepository.findByEmail(email).orElseThrow(() -> new CustomerNotFoundException(OrderExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    @Override
    public Customer findCustomerByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            throw new IllegalArgumentException("Phone number cannot be null");
        }
        return customerRepository.findByPhone(phoneNumber).orElseThrow(() -> new CustomerNotFoundException(OrderExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer registerNewCustomer(NewCustomerDto customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer data must not be null");
        }
        customerRepository.findByEmailOrPhone(customer.getEmail(), customer.getPhoneNumber()).ifPresent(foundCustomer -> validateCustomerData(foundCustomer, customer));

        Customer newCustomer = Customer.fromNewCustomerDto(customer);
        return customerRepository.save(newCustomer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long deleteCustomer(long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(OrderExceptionsMessages.CUSTOMER_NOT_FOUND));
        customerRepository.delete(customer);
        return customer.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer updateCustomerData(UpdateCustomerDataDto updateCustomerDataDto) {
        if (updateCustomerDataDto == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        Customer customer = customerRepository.findById(updateCustomerDataDto.getId())
                .orElseThrow(() -> new CustomerNotFoundException(OrderExceptionsMessages.CUSTOMER_NOT_FOUND));

        if (updateCustomerDataDto.getPhoneNumber() != null
                && !updateCustomerDataDto.getPhoneNumber().isEmpty()
                && !updateCustomerDataDto.getPhoneNumber().equals(customer.getPhoneNumber())
        ) {
            customer.setPhoneNumber(updateCustomerDataDto.getPhoneNumber());
        }

        if (updateCustomerDataDto.getEmail() != null
                && !updateCustomerDataDto.getEmail().isEmpty()
                && !updateCustomerDataDto.getEmail().equals(customer.getEmail())
        ) {
            customer.setEmail(updateCustomerDataDto.getEmail());
        }

        if (updateCustomerDataDto.getAddress() != null
                && !updateCustomerDataDto.getAddress().isEmpty()
                && !updateCustomerDataDto.getAddress().equals(customer.getAddress())
        ) {
            customer.setAddress(updateCustomerDataDto.getAddress());
        }

        return customerRepository.save(customer);
    }

    @Override
    public List<Order> getOrdersByCustomerId(long customerId) {
        return customerRepository.findById(customerId)
                .map(customer -> List.copyOf(customer.getOrders()))
                .orElseThrow(() -> new CustomerNotFoundException(OrderExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    @Override
    public List<Order> getOrdersByCustomerEmail(String customerEmail) {
        return customerRepository.findByEmail(customerEmail)
                .map(customer -> List.copyOf(customer.getOrders()))
                .orElseThrow(() -> new CustomerNotFoundException(OrderExceptionsMessages.CUSTOMER_NOT_FOUND));    }

    @Override
    public List<Order> getOrdersByCustomerPhoneNumber(String customerPhone) {
        return customerRepository.findByPhone(customerPhone)
                .map(customer -> List.copyOf(customer.getOrders()))
                .orElseThrow(() -> new CustomerNotFoundException(OrderExceptionsMessages.CUSTOMER_NOT_FOUND));    }

    private void validateCustomerData(Customer foundCustomer, NewCustomerDto newCustomer) {
        if (foundCustomer.getEmail().equals(newCustomer.getEmail())) {
            throw new CustomerEmailNotUniqueException(OrderExceptionsMessages.CUSTOMER_EMAIL_NOT_UNIQUE);
        }
        if (foundCustomer.getPhoneNumber().equals(newCustomer.getPhoneNumber())) {
            throw new CustomerPhoneNumberNotUniqueException(OrderExceptionsMessages.CUSTOMER_PHONE_NUMBER_NOT_UNIQUE);
        }
    }
}
