package org.localhost.pizzeria.order.system.customer.repository;

import org.localhost.pizzeria.order.system.customer.dto.CustomerConflictDto;
import org.localhost.pizzeria.order.system.customer.model.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerCrudRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhoneNumber(String phone);
    Optional<Customer> findByEmailOrPhoneNumber(String email, String phone);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phone);

    @Query("""
        SELECT new org.localhost.pizzeria.order.system.customer.dto.CustomerConflictDto(
            CASE WHEN EXISTS (SELECT 1 FROM Customer c WHERE c.email = :email) THEN true ELSE false END,
            CASE WHEN EXISTS (SELECT 1 FROM Customer c WHERE c.phoneNumber = :phoneNumber) THEN true ELSE false END
        )
    """)
    CustomerConflictDto checkCustomerDataConflicts(String email, String phoneNumber);
}
