package org.localhost.pizzeria.order.system.order.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.localhost.pizzeria.order.system.order.OrderStatus;
import org.localhost.pizzeria.order.system.customer.model.Customer;
import org.localhost.pizzeria.order.system.order.dto.NewOrderDto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders", schema = "ordering_system")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Order status cannot be null")
    private ZonedDateTime orderReceivedDate;

    private ZonedDateTime orderProcessingDate;

    private ZonedDateTime orderFinalizedDate;

    @NotNull
    @Column(precision = 10, scale = 2)
    private BigDecimal orderValue;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Order status cannot be null")
    private OrderStatus orderStatus;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @ElementCollection
    @CollectionTable(
            name = "order_pizza_ids",
            schema = "ordering_system",
            joinColumns = @JoinColumn(name = "order_id")
    )
    @Column(name = "pizza_id")
    private List<Long> pizzaIds = new ArrayList<>();


    public void addPizzaToPizzaList(long pizzaId) {
        pizzaIds.add(pizzaId);
    }

    public void removePizzaFromPizzaList(long pizzaId) {
        pizzaIds.remove(pizzaId);
    }

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

    public static Order fromNewOrderDto(NewOrderDto newOrderDto) {
        return Order.builder()
                .customer(newOrderDto.getCustomer())
                .orderValue(newOrderDto.getTotalPrice())
                .pizzaIds(newOrderDto.getPizzaIdList())
                .build();
    }

}
