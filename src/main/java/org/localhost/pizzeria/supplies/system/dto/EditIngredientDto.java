package org.localhost.pizzeria.supplies.system.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class EditIngredientDto {
    @NotBlank(message = "Name is mandatory")
    private String productName;

    @NotNull(message = "Minimum required amount cannot be null")
    @Min(value = 0, message = "Minimum required amount cannot be negative")
    private int minimumRequiredAmount;
}
