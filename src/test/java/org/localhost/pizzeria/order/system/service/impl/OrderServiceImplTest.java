package org.localhost.pizzeria.order.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.localhost.pizzeria.config.discounts.DiscountCatalog;
import org.localhost.pizzeria.config.discounts.DiscountParam;
import org.localhost.pizzeria.nats.publisher.Publisher;
import org.localhost.pizzeria.nats.subscriber.Subscriber;
import org.localhost.pizzeria.order.system.order.OrderStatus;
import org.localhost.pizzeria.order.system.customer.service.impl.CustomerServiceImpl;
import org.localhost.pizzeria.order.system.customer.dto.NewCustomerDto;
import org.localhost.pizzeria.order.system.order.dto.NewOrderDto;
import org.localhost.pizzeria.order.system.pizza.exceptions.NotEnoughIngredientsException;
import org.localhost.pizzeria.order.system.order.exceptions.messages.OrderExceptionsMessages;
import org.localhost.pizzeria.order.system.customer.model.Customer;
import org.localhost.pizzeria.order.system.order.model.Order;
import org.localhost.pizzeria.order.system.pizza.model.Pizza;
import org.localhost.pizzeria.order.system.order.service.impl.OrderPricingServiceImpl;
import org.localhost.pizzeria.order.system.order.service.impl.OrderServiceImpl;
import org.localhost.pizzeria.order.system.order.repository.OrderRepository;
import org.localhost.pizzeria.order.system.pizza.repository.PizzaRepository;
import org.localhost.pizzeria.order.system.customer.service.CustomerService;
import org.localhost.pizzeria.order.system.order.service.OrderPricingService;
import org.localhost.pizzeria.order.system.order.service.OrderService;
import org.localhost.pizzeria.order.system.pizza.service.PizzaService;
import org.localhost.pizzeria.order.system.pizza.service.impl.PizzaServiceImpl;
import org.localhost.pizzeria.order.system.service.impl.data.IngredientsTestData;
import org.localhost.pizzeria.order.system.service.impl.data.PizzaMenuTestData;
import org.localhost.pizzeria.supplies.system.repository.SupplySystemRepository;
import org.localhost.pizzeria.supplies.system.service.SupplySystemCommandService;
import org.localhost.pizzeria.supplies.system.service.SupplySystemQueryService;
import org.localhost.pizzeria.supplies.system.service.impl.InMemoryIngredientRepository;
import org.localhost.pizzeria.supplies.system.service.impl.SupplySystemCommandServiceImpl;
import org.localhost.pizzeria.supplies.system.service.impl.SupplySystemQueryServiceImpl;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class OrderServiceImplTest {

    private OrderService objectUnderTest;
    private OrderRepository orderRepository;
    private PizzaRepository pizzaRepository;
    private OrderPricingService orderPricingService;
    private SupplySystemRepository supplySystemRepository;
    private SupplySystemQueryService supplySystemQueryService;
    private SupplySystemCommandService supplySystemCommandService;
    private PizzaService pizzaService;
    private SimpMessagingTemplate messagingTemplate;
    CustomerService customerService;
    Subscriber subscribe;
    Publisher publisher;

    @BeforeEach
    void setUp() {
        DiscountCatalog discountCatalog = new DiscountCatalog();

        discountCatalog.registerNewDiscount(DiscountParam.builder()
                .discountLevel(new BigDecimal("0.10"))
                .ageLimit(10)
                .weekdays(List.of(DayOfWeek.FRIDAY))
                .studentStatusRequired(true)
                .build());

        discountCatalog.registerNewDiscount(DiscountParam.builder()
                .discountLevel(new BigDecimal("0.40"))
                .ageLimit(10)
                .weekdays(List.of(DayOfWeek.FRIDAY))
                .studentStatusRequired(true)
                .build());

        messagingTemplate = mock(SimpMessagingTemplate.class);
        orderRepository = new InMemoryOrderRepository();
        pizzaRepository = new InMemoryPizzaRepository();
        orderPricingService = new OrderPricingServiceImpl(discountCatalog);
        supplySystemRepository = new InMemoryIngredientRepository();
        supplySystemCommandService = new SupplySystemCommandServiceImpl(supplySystemRepository);
        supplySystemQueryService = new SupplySystemQueryServiceImpl(supplySystemRepository);
        pizzaService = new PizzaServiceImpl(pizzaRepository);
        customerService = new CustomerServiceImpl(new InMemoryCustomerRepository());
        objectUnderTest = new OrderServiceImpl(messagingTemplate, orderRepository, pizzaService, customerService, orderPricingService, supplySystemQueryService, supplySystemCommandService, subscribe, publisher);

        supplySystemRepository.saveAll(IngredientsTestData.getAllIngredients());
        PizzaMenuTestData.getAllPizzas().forEach(pizzaService::addPizzaToMenu);
    }


    @DisplayName("create orderShouldRegister a new order")
    @Test
    void createOrder() {
//        given
        List<Pizza> menu = pizzaService.getAllPizzas();
        Pizza firstSelecetedPizza = menu.get(0);
        Pizza secondSelecetedPizza = menu.get(1);
        BigDecimal totalPrice = firstSelecetedPizza.getPrice().add(secondSelecetedPizza.getPrice());

        List<Long> selectedPizzasIds = List.of(firstSelecetedPizza.getId(), secondSelecetedPizza.getId());


        NewCustomerDto newCustomerDto = NewCustomerDto.builder().firstName("testCustom").lastName("test last name").age(9).isStudent(true).address("test adress").email("test email").phoneNumber("+48999888777").build();
        Customer customer = customerService.registerNewCustomer(newCustomerDto);
        NewOrderDto newOrderDto = NewOrderDto.builder().customer(customer).pizzaIdList(selectedPizzasIds).build();
//        when
        Order testResult = objectUnderTest.createOrder(newOrderDto);
        //        then
        assertAll(
                () -> assertEquals(customer.getId(), testResult.getCustomer().getId()),
                () -> assertEquals(customer.getOrders(), testResult.getCustomer().getOrders()),
                () -> assertEquals(firstSelecetedPizza.getId(), testResult.getPizzaIds().get(0)),
                () -> assertEquals(secondSelecetedPizza.getId(), testResult.getPizzaIds().get(1)),
                () -> assertEquals(totalPrice, testResult.getOrderValue()),
                () -> assertNotNull(testResult.getOrderReceivedDate()),
                () -> assertEquals(OrderStatus.NEW, testResult.getOrderStatus())
        );
    }

    @DisplayName("createOrder should throw when not enough ingredients")
    @Test
    void createOrderWhenNotEnoughIngredients() {
//        given
        int FLOUR_BELOW_MINIMUM_INGREDIENTS_AMOUNT_MODIFIER = 99999;
        List<Pizza> menu = pizzaService.getAllPizzas();
        Pizza firstSelecetedPizza = menu.get(0);
        List<Long> selectedPizzasIds = List.of(firstSelecetedPizza.getId());

        supplySystemCommandService.decreaseIngredientStock(firstSelecetedPizza.getIngredients().get(0).getIngredientId(), FLOUR_BELOW_MINIMUM_INGREDIENTS_AMOUNT_MODIFIER);

        NewCustomerDto newCustomerDto = NewCustomerDto.builder().firstName("testCustom").lastName("test last name").address("test adress").email("test email").phoneNumber("+48999888777").build();
        Customer customer = customerService.registerNewCustomer(newCustomerDto);

        NewOrderDto newOrderDto = NewOrderDto.builder().customer(customer).pizzaIdList(selectedPizzasIds).build();
//        when
        NotEnoughIngredientsException testResult = assertThrows(
                NotEnoughIngredientsException.class,
                () -> objectUnderTest.createOrder(newOrderDto)
        );
        //        then
        assertAll(
                () -> assertEquals(OrderExceptionsMessages.NOT_ENOUGH_INGREDIENTS.getErrorMessage(), testResult.getMessage()),
                () -> assertEquals(OrderExceptionsMessages.NOT_ENOUGH_INGREDIENTS.getErrorCode(), testResult.getErrorCode())
        );
    }

    @Test
    void subscribeToIngredientCheck() {
    }
}