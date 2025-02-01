package org.localhost.pizzeria.order.system.service.impl;

import org.localhost.pizzeria.order.system.pizza.model.Pizza;
import org.localhost.pizzeria.order.system.pizza.repository.PizzaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryPizzaRepository implements PizzaRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryPizzaRepository.class);
    Map<Long, Pizza> pizzaMenu = new HashMap<>();
    AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Pizza save(Pizza pizza) {
        System.out.println(pizza.getName());
        if (pizza.getId() == null) {
            pizza.setId(idCounter.getAndIncrement());

        }
        pizzaMenu.put(pizza.getId(), pizza);
        return pizzaMenu.get(pizza.getId());
    }

    @Override
    public <S extends Pizza> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Pizza> findById(Long aLong) {
        return pizzaMenu.values().stream().filter(pizza -> pizza.getId().equals(aLong)).findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Pizza> findAll() {
        return pizzaMenu.values();
    }

    @Override
    public Iterable<Pizza> findAllById(Iterable<Long> pizzaIds) {
        List<Pizza> result = new ArrayList<>();
//        System.out.println("Current pizza menu: " + pizzaMenu.values());
//        System.out.println("Requested pizza IDs: " + pizzaIds);

        pizzaIds.forEach(pizzaId -> {
            Pizza pizza = pizzaMenu.get(pizzaId);
            if (pizza != null) {
                result.add(pizza);
            }
        });

//        System.out.println("Found pizzas: " + result);
        return result;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Pizza entity) {
        pizzaMenu.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Pizza> entities) {

    }

    @Override
    public void deleteAll() {
    }
}
