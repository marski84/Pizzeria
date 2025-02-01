package org.localhost.pizzeria.order.system.customer.service;

import org.localhost.pizzeria.order.system.customer.dto.NewCustomerDto;
import org.localhost.pizzeria.order.system.customer.dto.UpdateCustomerDataDto;
import org.localhost.pizzeria.order.system.customer.model.Customer;
import org.localhost.pizzeria.order.system.order.model.Order;

import java.util.List;

public interface CustomerService {
    Customer findCustomerByEmail(String email);
    Customer findCustomerByPhoneNumber(String phoneNumber);
    Customer registerNewCustomer(NewCustomerDto customer);
    long deleteCustomer(long customerId);
    Customer updateCustomerData(UpdateCustomerDataDto updateCustomerDataDto);
    void updateCustomerOrder(long customerId, Order order);
    List<Order> getOrdersByCustomerId(long customerId);
    List<Order> getOrdersByCustomerEmail(String customerEmail);
    List<Order> getOrdersByCustomerPhoneNumber(String customerPhone);
}
