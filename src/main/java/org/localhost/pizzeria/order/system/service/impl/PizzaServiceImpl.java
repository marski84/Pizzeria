package org.localhost.pizzeria.order.system.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.localhost.pizzeria.order.system.dto.NewPizzaDto;
import org.localhost.pizzeria.order.system.exceptions.OrderExceptionsMessages;
import org.localhost.pizzeria.order.system.exceptions.PizzaAlreadyAddedException;
import org.localhost.pizzeria.order.system.exceptions.PizzaNotFoundException;
import org.localhost.pizzeria.order.system.model.Pizza;
import org.localhost.pizzeria.order.system.repository.PizzaRepository;
import org.localhost.pizzeria.order.system.service.PizzaService;
import org.localhost.pizzeria.utils.ValidationUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PizzaServiceImpl implements PizzaService {
    private final PizzaRepository pizzaRepository;

    public PizzaServiceImpl(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Pizza addPizzaToMenu(NewPizzaDto newPizzaDto) {
        ValidationUtils.validateNotNull(newPizzaDto, "newPizzaDto");

        if (pizzaRepository.existsByName(newPizzaDto.getName())) {
            log.error("Pizza with name {} already exists", newPizzaDto.getName());
            throw new PizzaAlreadyAddedException(OrderExceptionsMessages.PIZZA_NAME_NOT_UNIQUE);
        }

        Pizza newPizza = Pizza.fromNewPizzaDto(newPizzaDto);
        log.info("New pizza: {}", newPizza.getName());

        return pizzaRepository.save(newPizza);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void removePizzaFromMenu(long pizzaId) {
        Pizza pizza = pizzaRepository.findById(pizzaId).orElseThrow(
                () -> new PizzaNotFoundException(OrderExceptionsMessages.PIZZA_NOT_FOUND)
        );
        pizzaRepository.delete(pizza);
    }

    @Override
    public Pizza getPizzaById(long pizzaId) {
        return pizzaRepository.findById(pizzaId).orElseThrow(
                () -> new PizzaNotFoundException(OrderExceptionsMessages.PIZZA_NOT_FOUND)
        );
    }

    @Override
    public List<Pizza> getAllPizzas() {
        List<Pizza> menu = new ArrayList<>();
        pizzaRepository.findAll().forEach(menu::add);
        return menu;
    }
}
