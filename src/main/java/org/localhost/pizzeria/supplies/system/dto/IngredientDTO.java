package org.localhost.pizzeria.supplies.system.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import org.localhost.pizzeria.supplies.system.model.Ingredient;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Product name can only contain letters, numbers, spaces and hyphens")
    private String productName;

    @NotNull(message = "Amount in stock is required")
    @Min(value = 0, message = "Amount in stock cannot be negative")
    @Max(value = 1000000, message = "Amount in stock cannot exceed 1,000,000")
    private int amountInStock;

    @NotNull(message = "Unit type is required")
    private Ingredient.UnitType unitType;

    @NotNull(message = "Minimum required amount is required")
    @Min(value = 0, message = "Minimum required amount cannot be negative")
    private int minimumRequiredAmount;

    private boolean needsRestock;

    public static IngredientDTO fromIngredient(Ingredient ingredient) {
        return IngredientDTO.builder()
                .id(ingredient.getId())
                .productName(ingredient.getProductName())
                .amountInStock(ingredient.getAmountInStock())
                .unitType(ingredient.getUnitType())
                .minimumRequiredAmount(ingredient.getMinimumRequiredAmount())
                .needsRestock(ingredient.needsRestock())
                .build();
    }

}
