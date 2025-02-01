package org.localhost.pizzeria.order.system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCustomerDto {
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Min(value = 5, message = "Client age must be greater than 5")
    private int age;

    private boolean isStudent = false;

    @NotBlank(message = "Address name is mandatory")
    private String address;

    @NotBlank(message = "Phone number name is mandatory")
    private String phoneNumber;

    @NotBlank(message = "email")
    private String email;
}
