package org.localhost.pizzeria.supplies.system.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String productName;
    private Long amountInStock;
    private Ingredient.UnitType unitType;
    private Long minimumRequiredAmount;
    boolean needsRestock;


    IngredientDTO from(Ingredient ingredient) {
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
