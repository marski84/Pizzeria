package org.localhost.pizzeria.order.system.service.impl.data;

import org.localhost.pizzeria.order.system.pizza.dto.NewPizzaDto;
import org.localhost.pizzeria.order.system.pizza.model.PizzaIngredient;

import java.math.BigDecimal;
import java.util.List;

import static org.localhost.pizzeria.order.system.service.impl.data.IngredientsTestData.*;

public final class PizzaMenuTestData {
    private PizzaMenuTestData() {}

    public static NewPizzaDto Margherita = NewPizzaDto.builder()
            .name("Margherita")
            .price(new BigDecimal("35.00"))
            .ingredients(
                    List.of(
                            new PizzaIngredient(flour.getId(), 200),
                            new PizzaIngredient(yeast.getId(), 20),
                            new PizzaIngredient(choppedTomatoes.getId(), 150),
                            new PizzaIngredient(mozzarella.getId(), 150),
                            new PizzaIngredient(freshBasil.getId(), 10),
                            new PizzaIngredient(oliveOil.getId(), 20)
                    )
            )
            .build();

    public static NewPizzaDto Marinara = NewPizzaDto.builder()
            .name("Marinara")
            .price(new BigDecimal("38.00"))
            .ingredients(
                    List.of(
                            new PizzaIngredient(flour.getId(), 200),
                            new PizzaIngredient(yeast.getId(), 20),
                            new PizzaIngredient(choppedTomatoes.getId(), 150),
                            new PizzaIngredient(granulatedGarlic.getId(), 15),
                            new PizzaIngredient(driedOregano.getId(), 10),
                            new PizzaIngredient(oliveOil.getId(), 20)
                    )
            )
            .build();

    public static NewPizzaDto ProsciuttoERucola = NewPizzaDto.builder()
            .name("Prosciutto e Rucola")
            .price(new BigDecimal("40.00"))
            .ingredients(
                    List.of(
                            new PizzaIngredient(flour.getId(), 200),
                            new PizzaIngredient(yeast.getId(), 20),
                            new PizzaIngredient(choppedTomatoes.getId(), 150),
                            new PizzaIngredient(mozzarella.getId(), 150),
                            new PizzaIngredient(prosciutto.getId(), 100)
                    )
            )
            .build();

    public static NewPizzaDto Diavola = NewPizzaDto.builder()
            .name("Diavola")
            .price(new BigDecimal("44.00"))
            .ingredients(
                    List.of(
                            new PizzaIngredient(flour.getId(), 200),
                            new PizzaIngredient(yeast.getId(), 20),
                            new PizzaIngredient(choppedTomatoes.getId(), 150),
                            new PizzaIngredient(mozzarella.getId(), 150),
                            new PizzaIngredient(salami.getId(), 100),
                            new PizzaIngredient(nduja.getId(), 50)
                    )
            )
            .build();

    public static NewPizzaDto Capricciosa = NewPizzaDto.builder()
            .name("Capricciosa")
            .price(new BigDecimal("48.00"))
            .ingredients(
                    List.of(
                            new PizzaIngredient(flour.getId(), 200),
                            new PizzaIngredient(yeast.getId(), 20),
                            new PizzaIngredient(choppedTomatoes.getId(), 150),
                            new PizzaIngredient(mozzarella.getId(), 150),
                            new PizzaIngredient(ham.getId(), 100)
                    )
            )
            .build();

    public static NewPizzaDto BBQChicken = NewPizzaDto.builder()
            .name("BBQ Chicken")
            .price(new BigDecimal("41.00"))
            .ingredients(
                    List.of(
                            new PizzaIngredient(flour.getId(), 200),
                            new PizzaIngredient(yeast.getId(), 20),
                            new PizzaIngredient(mozzarella.getId(), 150),
                            new PizzaIngredient(grilledChicken.getId(), 150)
                    )
            )
            .build();

    public static NewPizzaDto Pepperoni = NewPizzaDto.builder()
            .name("Pepperoni")
            .price(new BigDecimal("42.00"))
            .ingredients(
                    List.of(
                            new PizzaIngredient(flour.getId(), 200),
                            new PizzaIngredient(yeast.getId(), 20),
                            new PizzaIngredient(choppedTomatoes.getId(), 150),
                            new PizzaIngredient(mozzarella.getId(), 150),
                            new PizzaIngredient(pepperoni.getId(), 120)
                    )
            )
            .build();

    public static NewPizzaDto Chorizo = NewPizzaDto.builder()
            .name("Chorizo")
            .price(new BigDecimal("45.00"))
            .ingredients(
                    List.of(
                            new PizzaIngredient(flour.getId(), 200),
                            new PizzaIngredient(yeast.getId(), 20),
                            new PizzaIngredient(choppedTomatoes.getId(), 150),
                            new PizzaIngredient(mozzarella.getId(), 150),
                            new PizzaIngredient(chorizo.getId(), 100)
                    )
            )
            .build();

    public static NewPizzaDto Calabrese = NewPizzaDto.builder()
            .name("Calabrese")
            .price(new BigDecimal("48.00"))
            .ingredients(
                    List.of(
                            new PizzaIngredient(flour.getId(), 200),
                            new PizzaIngredient(yeast.getId(), 20),
                            new PizzaIngredient(choppedTomatoes.getId(), 150),
                            new PizzaIngredient(mozzarella.getId(), 150),
                            new PizzaIngredient(nduja.getId(), 80)
                    )
            )
            .build();

    public static NewPizzaDto BaconPizza = NewPizzaDto.builder()
            .name("Bacon")
            .price(new BigDecimal("39.00"))
            .ingredients(
                    List.of(
                            new PizzaIngredient(flour.getId(), 200),
                            new PizzaIngredient(yeast.getId(), 20),
                            new PizzaIngredient(choppedTomatoes.getId(), 150),
                            new PizzaIngredient(mozzarella.getId(), 150),
                            new PizzaIngredient(bacon.getId(), 100)
                    )
            )
            .build();

    public static List<NewPizzaDto> getAllPizzas() {
        return List.of(
                Margherita,
                Marinara,
                ProsciuttoERucola,
                Diavola,
                Capricciosa,
                BBQChicken,
                Pepperoni,
                Chorizo,
                Calabrese,
                BaconPizza
        );
    }
}