package org.localhost.pizzeria.supplies.system.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.localhost.pizzeria.supplies.system.model.Ingredient;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class NewIngredientDto {
    @NotBlank(message = "Name is mandatory")
    @Column(unique = true)
    private String productName;

    @NotNull(message = "Amount in stock cannot be null")
    @Min(value = 0, message = "Amount in stock cannot be negative")
    private int amountInStock;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Unit type cannot be null")
    private Ingredient.UnitType unitType;

    @NotNull(message = "Minimum required amount cannot be null")
    @Min(value = 0, message = "Minimum required amount cannot be negative")
    private int minimumRequiredAmount;
}
