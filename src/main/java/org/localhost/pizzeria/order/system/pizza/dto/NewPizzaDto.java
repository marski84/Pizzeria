package org.localhost.pizzeria.order.system.pizza.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.localhost.pizzeria.order.system.pizza.model.PizzaIngredient;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class NewPizzaDto {
    @NotNull(message = "Pizza name cannot be null!")
    @NotBlank
    @Size(min = 3, message = "Pizza name should be at least 3 letters long")
    private String name;

    @NotNull(message = "Pizza price cannot be null!")
    @Min(value = 1, message = "Pizza price must be greater than 0!")
    private BigDecimal price;

    @Size(min = 4, message = "Pizza ingredients should contain at least 4 ingredients")
    private List<PizzaIngredient> ingredients;

}
