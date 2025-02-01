package org.localhost.pizzeria.order.system.service.impl.data;
import org.localhost.pizzeria.supplies.system.model.Ingredient;

import java.util.List;

public final class IngredientsTestData {
    private IngredientsTestData() {}
    public static Ingredient flour = Ingredient.builder()
            .productName("Mąka do pizzy")
            .amountInStock(100000)
            .minimumRequiredAmount(20000)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static Ingredient milk = Ingredient.builder()
            .productName("Mleko")
            .amountInStock(100000)
            .minimumRequiredAmount(20000)
            .unitType(Ingredient.UnitType.MILLILITERS)
            .build();

    public static Ingredient choppedTomatoes = Ingredient.builder()
            .productName("Pomidory krojone")
            .amountInStock(50000)
            .minimumRequiredAmount(10000)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static Ingredient mozzarella = Ingredient.builder()
            .productName("Mozzarella")
            .amountInStock(30000)
            .minimumRequiredAmount(5000)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static Ingredient oliveOil = Ingredient.builder()
            .productName("Oliwa z oliwek")
            .amountInStock(5000)
            .minimumRequiredAmount(1000)
            .unitType(Ingredient.UnitType.MILLILITERS)
            .build();

    public static Ingredient yeast = Ingredient.builder()
            .productName("Drożdże")
            .amountInStock(1000)
            .minimumRequiredAmount(200)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static Ingredient freshBasil = Ingredient.builder()
            .productName("Bazylia świeża")
            .amountInStock(2000)
            .minimumRequiredAmount(500)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static Ingredient driedOregano = Ingredient.builder()
            .productName("Oregano suszone")
            .amountInStock(1500)
            .minimumRequiredAmount(300)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static Ingredient freshRosemary = Ingredient.builder()
            .productName("Rozmaryn świeży")
            .amountInStock(1000)
            .minimumRequiredAmount(200)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static Ingredient thyme = Ingredient.builder()
            .productName("Tymianek")
            .amountInStock(800)
            .minimumRequiredAmount(150)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static Ingredient granulatedGarlic = Ingredient.builder()
            .productName("Czosnek granulowany")
            .amountInStock(2000)
            .minimumRequiredAmount(400)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static Ingredient ham = Ingredient.builder()
            .productName("Szynka")
            .amountInStock(15000)
            .minimumRequiredAmount(3000)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static Ingredient pepperoni = Ingredient.builder()
            .productName("Pepperoni")
            .amountInStock(12000)
            .minimumRequiredAmount(2500)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static Ingredient chorizo = Ingredient.builder()
            .productName("Chorizo")
            .amountInStock(10000)
            .minimumRequiredAmount(2000)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static Ingredient salami = Ingredient.builder()
            .productName("Salami")
            .amountInStock(10000)
            .minimumRequiredAmount(2000)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static Ingredient bacon = Ingredient.builder()
            .productName("Boczek")
            .amountInStock(8000)
            .minimumRequiredAmount(1500)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static Ingredient grilledChicken = Ingredient.builder()
            .productName("Kurczak grillowany")
            .amountInStock(15000)
            .minimumRequiredAmount(3000)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static Ingredient prosciutto = Ingredient.builder()
            .productName("Prosciutto crudo")
            .amountInStock(5000)
            .minimumRequiredAmount(1000)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static Ingredient nduja = Ingredient.builder()
            .productName("Nduja")
            .amountInStock(3000)
            .minimumRequiredAmount(500)
            .unitType(Ingredient.UnitType.GRAMS)
            .build();

    public static List<Ingredient> getAllIngredients() {
        return List.of(
                flour,
                milk,
                choppedTomatoes,
                mozzarella,
                oliveOil,
                yeast,
                freshBasil,
                driedOregano,
                freshRosemary,
                thyme,
                granulatedGarlic,
                ham,
                pepperoni,
                chorizo,
                salami,
                bacon,
                grilledChicken,
                prosciutto,
                nduja
        );
    }
}
