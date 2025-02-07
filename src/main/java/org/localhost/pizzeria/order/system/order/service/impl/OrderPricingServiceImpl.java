package org.localhost.pizzeria.order.system.order.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.localhost.pizzeria.config.discounts.DiscountCatalog;
import org.localhost.pizzeria.config.discounts.DiscountParam;
import org.localhost.pizzeria.order.system.customer.model.Customer;
import org.localhost.pizzeria.order.system.pizza.model.Pizza;
import org.localhost.pizzeria.order.system.order.service.OrderPricingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
public class OrderPricingServiceImpl implements OrderPricingService {
    private final DiscountCatalog discountCatalog;

    public OrderPricingServiceImpl(DiscountCatalog discountCatalog) {
        this.discountCatalog = discountCatalog;
    }

    @Override
    public BigDecimal calculateOrderPrice(List<BigDecimal> orderPriceList, Customer customer) {
        BigDecimal priceBeforeDiscount = calculateBasePrice(orderPriceList);
        BigDecimal priceAfterDiscount = applyDiscounts(priceBeforeDiscount, customer);
        
        return priceBeforeDiscount;
    }

    private BigDecimal calculateBasePrice(List<BigDecimal> orderedPizzaList) {
        BigDecimal totalPrice = orderedPizzaList.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalPrice;
    }

    private BigDecimal applyDiscounts(BigDecimal basePrice, Customer customer) {
        ZonedDateTime currentDate = ZonedDateTime.now();

        BigDecimal discountModifier = discountCatalog.getDiscountList().stream()
                .filter(discountParam -> discountParam.isApplicable(customer.getAge(), customer.isStudent(), currentDate.getDayOfWeek()))
                .map(DiscountParam::getDiscountLevel)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        BigDecimal finalPrice = basePrice.subtract(
                basePrice.multiply(discountModifier)
        );

        log.info("Base price: {}, Total discount: {}, Final price: {}",
                basePrice, discountModifier, finalPrice);

        return finalPrice;
    }
}
