package org.localhost.pizzeria.order.system.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UpdateCustomerDataDto {
    @NotBlank(message = "id cannot be empty")
    @NotNull(message = "id cannot be null!")
    private long id;

    @NotBlank(message = "Phone number is mandatory")
    @NotNull(message = "Phone cannot be null!")
    private String phoneNumber;

    @NotNull(message = "email cannot be null!")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotNull(message = "Address cannot be null!")
    @NotBlank(message = "Address is mandatory")
    private String address;
}
