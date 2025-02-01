package org.localhost.pizzeria.order.system.customer.dto;

import lombok.Value;

@Value
public class CustomerConflictDto {
    boolean emailConflict;
    boolean phoneNumberConflict;
}
