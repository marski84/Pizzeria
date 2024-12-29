package org.localhost.pizzeria.supplies.system.repository;

import org.localhost.pizzeria.supplies.system.model.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplySystemRepository extends CrudRepository<Ingredient, Long> {

}
