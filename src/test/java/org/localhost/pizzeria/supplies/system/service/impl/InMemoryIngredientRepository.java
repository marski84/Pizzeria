package org.localhost.pizzeria.supplies.system.service.impl;

import org.localhost.pizzeria.supplies.system.model.Ingredient;
import org.localhost.pizzeria.supplies.system.repository.SupplySystemRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryIngredientRepository implements SupplySystemRepository {
    Map<Long, Ingredient> ingredients = new HashMap<>();
    AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Ingredient save(Ingredient entity) {
        if (entity.getId() == null) {
            entity.setId(idGenerator.getAndIncrement());
        }
        ingredients.put(entity.getId(), entity);
        return ingredients.get(entity.getId());
    }

    @Override
    public <S extends Ingredient> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Ingredient> findById(Long id) {
        return ingredients.values().stream().filter(ingredient -> ingredient.getId() == id).findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return ingredients.values().stream().toList();
    }

    @Override
    public Iterable<Ingredient> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Ingredient entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Ingredient> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
