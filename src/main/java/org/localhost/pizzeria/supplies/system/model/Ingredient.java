package org.localhost.pizzeria.supplies.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ingredients", schema = "supply_system")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Column(unique = true)
    private String productName;

    @NotNull(message = "Amount in stock cannot be null")
    @Min(value = 0, message = "Amount in stock cannot be negative")
    private int amountInStock;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Unit type cannot be null")
    @Column(columnDefinition = "varchar(255)")
    private UnitType unitType;

    @NotNull(message = "Minimum required amount cannot be null")
    @Min(value = 0, message = "Minimum required amount cannot be negative")
    private int minimumRequiredAmount;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }

    public boolean needsRestock() {
        return amountInStock <= minimumRequiredAmount;
    }

    public boolean isStockSufficient(int requiredAmount) {
        return amountInStock >= requiredAmount;
    }

    public enum UnitType {
        GRAMS,
        KILOGRAMS,
        MILLILITERS,
        LITERS,
        PIECES,
        PACKAGES
    }
}