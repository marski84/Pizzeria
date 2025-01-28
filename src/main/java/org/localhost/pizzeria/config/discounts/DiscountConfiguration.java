package org.localhost.pizzeria.config.discounts;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.List;

@Configuration
public class DiscountConfiguration {

    @Bean
    public DiscountCatalogTemplate discountCatalog() {
        DiscountCatalogTemplate discountCatalog = new DiscountCatalogTemplate();

        discountCatalog.registerNewDiscount(DiscountParam.builder()
                .discountLevel(new BigDecimal("0.1"))
                .ageLimit(10)
                .weekdays(List.of(DayOfWeek.TUESDAY))
                .studentStatusRequired(false)
                .build());

        discountCatalog.registerNewDiscount(DiscountParam.builder()
                .discountLevel(new BigDecimal("0.40"))
                .ageLimit(0)
                .weekdays(List.of(DayOfWeek.THURSDAY))
                .studentStatusRequired(true)
                .build());


        return discountCatalog;
    }
}
