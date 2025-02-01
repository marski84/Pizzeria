package org.localhost.pizzeria.order.system.pizza.service.impl;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.localhost.pizzeria.order.system.pizza.dto.NewPizzaDto;
import org.localhost.pizzeria.order.system.order.exceptions.messages.OrderExceptionsMessages;
import org.localhost.pizzeria.order.system.pizza.exceptions.PizzaAlreadyAddedException;
import org.localhost.pizzeria.order.system.pizza.exceptions.PizzaNotFoundException;
import org.localhost.pizzeria.order.system.pizza.model.Pizza;
import org.localhost.pizzeria.order.system.pizza.repository.PizzaRepository;
import org.localhost.pizzeria.order.system.pizza.service.PizzaService;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            Pizza newPizza = Pizza.fromNewPizzaDto(newPizzaDto);
            log.info("New pizza: {}", newPizza.getName());
            return pizzaRepository.save(newPizza);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException
                    && e.getMessage().contains("uk_pizza_name")) {
                log.error("Pizza with name {} already exists", newPizzaDto.getName());
                throw new PizzaAlreadyAddedException(OrderExceptionsMessages.PIZZA_NAME_NOT_UNIQUE);
            }
            throw e;
        }
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
