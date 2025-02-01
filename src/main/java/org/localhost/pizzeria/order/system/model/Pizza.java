package org.localhost.pizzeria.order.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.localhost.pizzeria.order.system.dto.NewPizzaDto;
import org.localhost.pizzeria.order.system.dto.PizzaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pizzas", schema = "ordering_system")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pizza {
    private static final Logger log = LoggerFactory.getLogger(Pizza.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    @Size(min = 4, max = 20)
    @ElementCollection
    @CollectionTable(
            name = "pizza_ingredients",
            schema = "ordering_system",
            joinColumns = @JoinColumn(name = "pizza_id")
    )
    private List<PizzaIngredient> ingredients = new ArrayList<>();

    public void addIngredient(Long ingredientId, Integer amount) {
        ingredients.add(PizzaIngredient.create(ingredientId, amount));
    }

    public void removeIngredient(Long ingredientId) {
        ingredients.removeIf(ingredient -> ingredient.getIngredientId().equals(ingredientId));
    }

    public static Pizza fromNewPizzaDto(NewPizzaDto pizzaDto) {
        return Pizza.builder()
                .name(pizzaDto.getName())
                .price(pizzaDto.getPrice())
                .ingredients(pizzaDto.getIngredients())
                .build();
    }

}