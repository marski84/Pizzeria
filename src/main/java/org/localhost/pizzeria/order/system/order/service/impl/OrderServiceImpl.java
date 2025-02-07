package org.localhost.pizzeria.order.system.order.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.localhost.pizzeria.nats.publisher.Publisher;
import org.localhost.pizzeria.nats.subscriber.Subscriber;
import org.localhost.pizzeria.order.system.order.dto.NewOrderDto;
import org.localhost.pizzeria.order.system.order.repository.OrderRepository;
import org.localhost.pizzeria.order.system.pizza.exceptions.NotEnoughIngredientsException;
import org.localhost.pizzeria.order.system.order.exceptions.messages.OrderExceptionsMessages;
import org.localhost.pizzeria.order.system.order.model.Order;
import org.localhost.pizzeria.order.system.pizza.model.Pizza;
import org.localhost.pizzeria.order.system.pizza.model.PizzaIngredient;
import org.localhost.pizzeria.order.system.order.service.OrderService;
import org.localhost.pizzeria.order.system.pizza.service.PizzaService;
import org.localhost.pizzeria.supplies.system.service.SupplySystemCommandService;
import org.localhost.pizzeria.supplies.system.service.SupplySystemQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final PizzaService pizzaService;
    private final SupplySystemQueryService supplySystemQueryService;
    private final SupplySystemCommandService supplySystemCommandService;

    public OrderServiceImpl(OrderRepository orderRepository, PizzaService pizzaService, SupplySystemQueryService supplySystemQueryService, SupplySystemCommandService supplySystemCommandService) {
        this.orderRepository = orderRepository;
        this.pizzaService = pizzaService;
        this.supplySystemQueryService = supplySystemQueryService;
        this.supplySystemCommandService = supplySystemCommandService;
    }


    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(NewOrderDto orderDto) {
        List<Pizza> orderedPizzaList = orderDto.getPizzaIdList().stream()
                .map(pizzaService::getPizzaById)
                .toList();

        List<PizzaIngredient> ingredientList = orderedPizzaList.stream()
                .flatMap(pizza -> pizza.getIngredients().stream())
                .toList();


        boolean ingredientAvailableInSupply = ingredientList.stream()
                .allMatch(ingredient -> supplySystemQueryService.isIngredientInStock(ingredient.getIngredientId(), ingredient.getAmount()));

        if (!ingredientAvailableInSupply) {
//            this.supplySystemCommandService.submitOrderToStockQueue()
//            TODO implement CRON action
            throw new NotEnoughIngredientsException(OrderExceptionsMessages.NOT_ENOUGH_INGREDIENTS);
        }

//        BigDecimal totalPrice = orderPricingService.calculateOrderPrice(orderedPizzaList, orderDto.getCustomer());
//        orderDto.setTotalPrice(totalPrice);

//        TODO wrzucić na NATS dla kucharza

        Order newOrder = Order.fromNewOrderDto(orderDto);
        return orderRepository.save(newOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteOrder(id);
    }

    // NATS Subscriber
    @PostConstruct
    public void subscribeToIngredientCheck() {
//        subscriber.subscribe("ingredients.status");

    }
}
