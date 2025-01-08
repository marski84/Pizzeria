package org.localhost.pizzeria.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.localhost.pizzeria.supplies.system.model.Ingredient;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyCheckMessage implements Serializable {
    private List<Ingredient> ingredientsToRestock;
}
