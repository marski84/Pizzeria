package org.localhost.pizzeria.order.system.service.impl;

import lombok.extern.slf4j.Slf4j;
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
import org.localhost.pizzeria.utils.ValidationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        ValidationUtils.validateNotNull(email, "email");
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        return customerRepository.findByEmail(email).orElseThrow(() -> new CustomerNotFoundException(OrderExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    @Override
    public Customer findCustomerByPhoneNumber(String phoneNumber) {
        ValidationUtils.validateNotNull(phoneNumber, "phoneNumber");
        Objects.requireNonNull(phoneNumber, "Phone number cannot be null");
        return customerRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new CustomerNotFoundException(OrderExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer registerNewCustomer(NewCustomerDto customer) {
        ValidationUtils.validateNotNull(customer, "customer");

        customerRepository.findByEmailOrPhoneNumber(customer.getEmail(), customer.getPhoneNumber()).ifPresent(foundCustomer -> validateCustomerUniqueness(foundCustomer, customer));

        Customer newCustomer = Customer.fromNewCustomerDto(customer);
        log.info("New customer registered with id: {}", newCustomer.getId());

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
        ValidationUtils.validateNotNull(updateCustomerDataDto, "updateCustomerDataDto");

        Customer customer = customerRepository.findById(updateCustomerDataDto.getId()).orElseThrow(() -> new CustomerNotFoundException(OrderExceptionsMessages.CUSTOMER_NOT_FOUND));

        if (!updateCustomerDataDto.getPhoneNumber().equals(customer.getPhoneNumber())) {
            if (customerRepository.existsByPhoneNumber(updateCustomerDataDto.getPhoneNumber())) {
                throw new CustomerPhoneNumberNotUniqueException(OrderExceptionsMessages.CUSTOMER_PHONE_NUMBER_NOT_UNIQUE);
            }
            customer.setPhoneNumber(updateCustomerDataDto.getPhoneNumber());
        }

        if (!updateCustomerDataDto.getEmail().equals(customer.getEmail())) {
            if (customerRepository.existsByEmail(updateCustomerDataDto.getEmail())) {
                throw new CustomerEmailNotUniqueException(OrderExceptionsMessages.CUSTOMER_EMAIL_NOT_UNIQUE);
            }
            customer.setEmail(updateCustomerDataDto.getEmail());
        }

        if (!updateCustomerDataDto.getAddress().equals(customer.getAddress())) {
            customer.setAddress(updateCustomerDataDto.getAddress());
        }

        return customerRepository.save(customer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCustomerOrder(long customerId, Order order) {
        ValidationUtils.validateNotNull(order, "order");

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(OrderExceptionsMessages.CUSTOMER_NOT_FOUND));
        customer.addOrder(order);
        customerRepository.save(customer);
    }

    @Override
    public List<Order> getOrdersByCustomerId(long customerId) {
        return customerRepository.findById(customerId).map(customer -> List.copyOf(customer.getOrders())).orElseThrow(() -> new CustomerNotFoundException(OrderExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    @Override
    public List<Order> getOrdersByCustomerEmail(String customerEmail) {
        ValidationUtils.validateNotNull(customerEmail, "customerEmail");

        return customerRepository.findByEmail(customerEmail).map(customer -> List.copyOf(customer.getOrders())).orElseThrow(() -> new CustomerNotFoundException(OrderExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    @Override
    public List<Order> getOrdersByCustomerPhoneNumber(String customerPhone) {
        ValidationUtils.validateNotNull(customerPhone, "customerPhone");

        return customerRepository.findByPhoneNumber(customerPhone).map(customer -> List.copyOf(customer.getOrders())).orElseThrow(() -> new CustomerNotFoundException(OrderExceptionsMessages.CUSTOMER_NOT_FOUND));
    }

    private void validateCustomerUniqueness(Customer foundCustomer, NewCustomerDto newCustomer) {
        if (foundCustomer.getEmail().equals(newCustomer.getEmail())) {
            log.error("Not unique email for {}", newCustomer);
            throw new CustomerEmailNotUniqueException(OrderExceptionsMessages.CUSTOMER_EMAIL_NOT_UNIQUE);
        }
        if (foundCustomer.getPhoneNumber().equals(newCustomer.getPhoneNumber())) {
            log.error("Not unique phone number for {}", newCustomer);
            throw new CustomerPhoneNumberNotUniqueException(OrderExceptionsMessages.CUSTOMER_PHONE_NUMBER_NOT_UNIQUE);
        }
    }


}
