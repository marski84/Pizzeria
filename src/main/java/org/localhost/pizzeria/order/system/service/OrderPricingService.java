package org.localhost.pizzeria.order.system.service;

import org.localhost.pizzeria.order.system.model.Customer;
import org.localhost.pizzeria.order.system.model.Pizza;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface OrderPricingService {
    BigDecimal calculateOrderPrice(List<Pizza> orderedPizzaList, Customer customer);
}
