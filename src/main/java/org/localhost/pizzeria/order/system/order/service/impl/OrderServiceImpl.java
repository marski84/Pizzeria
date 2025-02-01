package org.localhost.pizzeria.order.system.order.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.localhost.pizzeria.nats.publisher.Publisher;
import org.localhost.pizzeria.nats.subscriber.Subscriber;
import org.localhost.pizzeria.order.system.order.dto.NewOrderDto;
import org.localhost.pizzeria.order.system.pizza.exceptions.NotEnoughIngredientsException;
import org.localhost.pizzeria.order.system.order.exceptions.messages.OrderExceptionsMessages;
import org.localhost.pizzeria.order.system.customer.model.Customer;
import org.localhost.pizzeria.order.system.order.model.Order;
import org.localhost.pizzeria.order.system.pizza.model.Pizza;
import org.localhost.pizzeria.order.system.pizza.model.PizzaIngredient;
import org.localhost.pizzeria.order.system.order.repository.OrderRepository;
import org.localhost.pizzeria.order.system.customer.service.CustomerService;
import org.localhost.pizzeria.order.system.order.service.OrderPricingService;
import org.localhost.pizzeria.order.system.order.service.OrderService;
import org.localhost.pizzeria.order.system.pizza.service.PizzaService;
import org.localhost.pizzeria.supplies.system.service.SupplySystemCommandService;
import org.localhost.pizzeria.supplies.system.service.SupplySystemQueryService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final SimpMessagingTemplate messagingTemplate;
    private final OrderRepository orderRepository;
    private final PizzaService pizzaService;
    private final CustomerService customerService;
    private final OrderPricingService orderPricingService;
    private final SupplySystemQueryService supplySystemQueryService;
    private final SupplySystemCommandService supplySystemCommandService;
    private final Subscriber subscriber;
    private final Publisher publisher;

    public OrderServiceImpl(SimpMessagingTemplate messagingTemplate, OrderRepository orderRepository, PizzaService pizzaService, CustomerService customerService, OrderPricingService orderPricingService, SupplySystemQueryService supplySystemQueryService, SupplySystemCommandService supplySystemCommandService, Subscriber subscriber, Publisher publisher) {
        this.messagingTemplate = messagingTemplate;
        this.orderRepository = orderRepository;
        this.pizzaService = pizzaService;
        this.customerService = customerService;
        this.orderPricingService = orderPricingService;
        this.supplySystemQueryService = supplySystemQueryService;
        this.supplySystemCommandService = supplySystemCommandService;
        this.subscriber = subscriber;
        this.publisher = publisher;
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

        BigDecimal totalPrice = orderPricingService.calculateOrderPrice(orderedPizzaList, orderDto.getCustomer());
        orderDto.setTotalPrice(totalPrice);

        ingredientList.forEach(i -> supplySystemCommandService.decreaseIngredientStock(i.getIngredientId(), i.getAmount()));

//        TODO wrzucić na NATS dla kucharza

        Order newOrder = Order.fromNewOrderDto(orderDto);

        Customer customer = orderDto.getCustomer();
        customer.addOrder(newOrder);
        customerService.updateCustomerOrder(customer.getId(), newOrder);

        return orderRepository.save(newOrder);
    }

    // NATS Subscriber
    @PostConstruct
    public void subscribeToIngredientCheck() {
        subscriber.subscribe("ingredients.status");

    }
}
