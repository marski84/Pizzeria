package org.localhost.pizzeria.order.system.customer.service;

import org.localhost.pizzeria.order.system.customer.dto.NewCustomerDto;
import org.localhost.pizzeria.order.system.customer.dto.UpdateCustomerDataDto;
import org.localhost.pizzeria.order.system.customer.model.Customer;

public interface CustomerService {
    Customer findCustomerByEmail(String email);
    Customer findCustomerByPhoneNumber(String phoneNumber);
    Customer findCustomerById(Long id);
    Customer registerNewCustomer(NewCustomerDto customer);
    long deleteCustomer(long customerId);
    Customer updateCustomerData(UpdateCustomerDataDto updateCustomerDataDto);
}
