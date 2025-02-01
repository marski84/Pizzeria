package org.localhost.pizzeria.supplies.inventory.notification.dto;

import jakarta.persistence.Embeddable;
import lombok.*;
import org.localhost.pizzeria.supplies.system.model.Ingredient;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestockInventoryItem {
    private Long productId;
    private String productName;
    private int quantityToOrder;

    public static RestockInventoryItem fromIngredient(Ingredient ingredient) {
        return RestockInventoryItem.builder()
                .productId(ingredient.getId())
                .productName(ingredient.getProductName())
                .quantityToOrder(ingredient.getMinimumRequiredAmount() - ingredient.getAmountInStock())
                .build();
    }


}
