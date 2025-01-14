package org.localhost.pizzeria.order.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.localhost.pizzeria.order.system.OrderStatus;

import java.time.ZonedDateTime;

@Entity
@Table(name = "customers", schema = "ordering_system")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Order status cannot be null")
    private ZonedDateTime orderReceivedDate;

    private ZonedDateTime orderProcessingDate;

    private ZonedDateTime orderFinalizedDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Order status cannot be null")
    private OrderStatus orderStatus;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @PrePersist
    protected void onCreate() {
        this.orderReceivedDate = ZonedDateTime.now();
        this.orderStatus = OrderStatus.NEW;
    }

    @PreUpdate
    protected void onUpdate() {
        if (OrderStatus.PROCESSING.equals(this.orderStatus)) {
            this.orderProcessingDate = ZonedDateTime.now();
        } else if (OrderStatus.FINALIZED.equals(this.orderStatus)) {
            this.orderFinalizedDate = ZonedDateTime.now();
        }
    }

}
