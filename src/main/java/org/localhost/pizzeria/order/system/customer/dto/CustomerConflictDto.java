package org.localhost.pizzeria.order.system.customer.dto;

public record CustomerConflictDto(boolean emailConflict, boolean phoneNumberConflict) {
}
