package org.localhost.pizzeria.order.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.localhost.pizzeria.config.discounts.DiscountCatalogTemplate;
import org.localhost.pizzeria.config.discounts.DiscountConfiguration;
import org.localhost.pizzeria.config.discounts.DiscountParam;
import org.localhost.pizzeria.order.system.model.Customer;
import org.localhost.pizzeria.order.system.model.Pizza;
import org.localhost.pizzeria.order.system.service.OrderPricingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class OrderPricingServiceImpl implements OrderPricingService {
    private final DiscountCatalogTemplate discountCatalogTemplate;

    public OrderPricingServiceImpl(DiscountCatalogTemplate discountCatalogTemplate) {
        this.discountCatalogTemplate = discountCatalogTemplate;
    }

    @Override
    public BigDecimal calculateOrderPrice(List<Pizza> orderedPizzaList, Customer customer) {
        BigDecimal priceBeforeDiscount = calculateBasePrice(orderedPizzaList);
        BigDecimal priceAfterDiscount = applyDiscounts(priceBeforeDiscount, customer);
        
        return priceBeforeDiscount;
    }

    private BigDecimal calculateBasePrice(List<Pizza> orderedPizzaList) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (Pizza pizza : orderedPizzaList) {
            totalPrice = totalPrice.add(pizza.getPrice());
        }
        return totalPrice;
    }

    private BigDecimal applyDiscounts(BigDecimal basePrice, Customer customer) {
        ZonedDateTime currentDate = ZonedDateTime.now();
        BigDecimal totalDiscountLevel = BigDecimal.ZERO;

        for (DiscountParam discountParam : discountCatalogTemplate.getDiscountList()) {
            log.info("Checking discount: {}", discountParam.getDiscountLevel());
            log.info("Customer age: {}", customer.getAge());

            if (discountParam.isApplicable(customer.getAge(), customer.isStudent(), currentDate.getDayOfWeek())) {
                totalDiscountLevel = totalDiscountLevel.add(discountParam.getDiscountLevel());
                log.info("Accumulated discount level: {}", totalDiscountLevel);
            }
        }

        BigDecimal finalPrice = basePrice.subtract(
                basePrice.multiply(totalDiscountLevel)
        );

        log.info("Base price: {}, Total discount: {}, Final price: {}",
                basePrice, totalDiscountLevel, finalPrice);

        return finalPrice;
    }
}
