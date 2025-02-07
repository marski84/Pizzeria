package org.localhost.pizzeria.order.system.customer.repository;
import org.localhost.pizzeria.order.system.customer.dto.CustomerConflictDto;
import org.localhost.pizzeria.order.system.customer.model.Customer;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository {

    Customer findByEmail(String email);

    Customer findByPhoneNumber(String phoneNumber);

    Customer findByEmailOrPhoneNumber(String email, String phoneNumber);

    Customer save(Customer newCustomer);

    Customer findById(long customerId);

    void delete(Customer customer);

    CustomerConflictDto checkCustomerDataConflicts(String email, String phoneNumber);
}
