package org.localhost.pizzeria.order.system.operator.service.impl;

import org.localhost.pizzeria.order.system.customer.model.Customer;
import org.localhost.pizzeria.order.system.customer.service.CustomerService;
import org.localhost.pizzeria.order.system.operator.service.OperatorCommandService;
import org.localhost.pizzeria.order.system.order.dto.NewOrderDto;
import org.localhost.pizzeria.order.system.order.model.Order;
import org.localhost.pizzeria.order.system.order.service.OrderPricingService;
import org.localhost.pizzeria.order.system.order.service.OrderService;
import org.localhost.pizzeria.order.system.pizza.model.Pizza;
import org.localhost.pizzeria.order.system.pizza.service.PizzaService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OperatorCommandServiceImpl implements OperatorCommandService {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final OrderPricingService orderPricingService;
    private final PizzaService pizzaService;

    public OperatorCommandServiceImpl(CustomerService customerService, OrderService orderService, OrderPricingService orderPricingService, PizzaService pizzaService) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.orderPricingService = orderPricingService;
        this.pizzaService = pizzaService;
    }


    @Override
    public Order registerNewOrder(NewOrderDto order) {
        Customer customer = customerService.findCustomerById(order.getCustomerId());
        List<BigDecimal> prices = order.getPizzaIdList().stream()
                .map(pizzaService::getPizzaById)
                .map(Pizza::getPrice)
                .toList();

        BigDecimal totalOrderPrice = orderPricingService.calculateOrderPrice(prices, customer);
        order.setOrderPrice(totalOrderPrice);

        return orderService.createOrder(order);

    }

    @Override
    public void deleteOrder(Long id) {
        orderService.deleteOrder(id);
    }

    @Override
    public Order updateOrder(Order order) {
        return null;
    }
}
