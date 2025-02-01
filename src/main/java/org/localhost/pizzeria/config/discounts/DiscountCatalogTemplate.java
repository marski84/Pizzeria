package org.localhost.pizzeria.config.discounts;

import java.util.ArrayList;
import java.util.List;



public class DiscountCatalogTemplate {

    private List<DiscountParam> discountParams = new ArrayList<>();

    public List<DiscountParam> getDiscountList() {
        System.out.println(discountParams.size());
        return List.copyOf(discountParams);
    }

    public void registerNewDiscount(DiscountParam discountParam) {
        discountParams.add(discountParam);
    }



}
