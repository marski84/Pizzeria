package org.localhost.pizzeria.order.system.order.service;

import org.localhost.pizzeria.order.system.customer.model.Customer;
import org.localhost.pizzeria.order.system.pizza.model.Pizza;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface OrderPricingService {
    BigDecimal calculateOrderPrice(List<BigDecimal> orderedPriceList, Customer customer);
}
