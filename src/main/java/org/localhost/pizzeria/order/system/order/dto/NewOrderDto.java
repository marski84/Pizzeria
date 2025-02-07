package org.localhost.pizzeria.order.system.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class NewOrderDto {
    @NotNull(message = "Customer id cannot be null!")
    @Min(value = 1, message = "Customer id must be greater then zero!")
    private Long customerId;

    @NotNull(message = "Total order price cannot be null")
    @Min(value = 1,message = "Order value must be greater then zero!")
    private BigDecimal totalPrice;

    @NotNull(message = "Pizza list cannot be null!")
    @Size(min = 1, message = "Pizza order list must contain at least 1 position!")
    private List<Long> pizzaIdList;

    public void setOrderPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Builder
    public NewOrderDto(Long customerId, List<Long> pizzaIdList) {
        this.customerId = customerId;
        this.pizzaIdList = pizzaIdList;
    }
}
