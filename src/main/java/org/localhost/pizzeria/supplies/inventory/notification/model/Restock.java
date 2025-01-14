package org.localhost.pizzeria.supplies.inventory.notification.model;

import jakarta.persistence.*;
import lombok.*;
import org.localhost.pizzeria.supplies.inventory.notification.dto.RestockInventoryItem;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "restock_orders", schema = "inventory")
public class Restock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ZonedDateTime createdAt;

    private boolean processed;

    @ElementCollection
    @CollectionTable(
            name = "restock_items",
            joinColumns = @JoinColumn(name = "restock_id")
    )
    private List<RestockInventoryItem> restockInventoryItems;


    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
        this.processed = false;
    }

}
