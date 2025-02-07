package org.localhost.pizzeria.order.system.operator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.localhost.pizzeria.config.discounts.DiscountCatalog;
import org.localhost.pizzeria.config.discounts.DiscountParam;
import org.localhost.pizzeria.order.system.customer.dto.NewCustomerDto;
import org.localhost.pizzeria.order.system.customer.model.Customer;
import org.localhost.pizzeria.order.system.customer.repository.CustomerRepository;
import org.localhost.pizzeria.order.system.customer.service.CustomerService;
import org.localhost.pizzeria.order.system.customer.service.impl.CustomerServiceImpl;
import org.localhost.pizzeria.order.system.operator.service.OperatorCommandService;
import org.localhost.pizzeria.order.system.order.OrderStatus;
import org.localhost.pizzeria.order.system.order.dto.NewOrderDto;
import org.localhost.pizzeria.order.system.order.exceptions.OrderNotFoundException;
import org.localhost.pizzeria.order.system.order.model.Order;
import org.localhost.pizzeria.order.system.order.repository.OrderRepository;
import org.localhost.pizzeria.order.system.order.service.OrderPricingService;
import org.localhost.pizzeria.order.system.order.service.OrderService;
import org.localhost.pizzeria.order.system.order.service.impl.OrderPricingServiceImpl;
import org.localhost.pizzeria.order.system.order.service.impl.OrderServiceImpl;
import org.localhost.pizzeria.order.system.pizza.model.Pizza;
import org.localhost.pizzeria.order.system.pizza.repository.PizzaRepository;
import org.localhost.pizzeria.order.system.pizza.service.PizzaService;
import org.localhost.pizzeria.order.system.pizza.service.impl.PizzaServiceImpl;
import org.localhost.pizzeria.order.system.service.impl.InMemoryCustomerCrudRepository;
import org.localhost.pizzeria.order.system.service.impl.InMemoryOrderRepository;
import org.localhost.pizzeria.order.system.service.impl.InMemoryPizzaRepository;
import org.localhost.pizzeria.order.system.service.impl.data.IngredientsTestData;
import org.localhost.pizzeria.order.system.service.impl.data.PizzaMenuTestData;
import org.localhost.pizzeria.supplies.system.repository.SupplySystemRepository;
import org.localhost.pizzeria.supplies.system.service.SupplySystemCommandService;
import org.localhost.pizzeria.supplies.system.service.SupplySystemQueryService;
import org.localhost.pizzeria.supplies.system.service.impl.InMemoryIngredientRepository;
import org.localhost.pizzeria.supplies.system.service.impl.SupplySystemCommandServiceImpl;
import org.localhost.pizzeria.supplies.system.service.impl.SupplySystemQueryServiceImpl;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class OperatorCommandServiceImplTest {

    private OperatorCommandService objectUnderTest;
    private CustomerService customerService;
    private OrderService orderService;
    private OrderPricingService orderPricingService;
    private PizzaService pizzaService;
    private SupplySystemQueryService supplySystemQueryService;
    private SupplySystemCommandService supplySystemCommandService;
    private OrderRepository inMemoryOrderRepository;

    @BeforeEach
    void setUp() {
        CustomerRepository inMemoryCustomerCrudRepository = new InMemoryCustomerCrudRepository();
        inMemoryOrderRepository = new InMemoryOrderRepository();
        customerService = new CustomerServiceImpl(inMemoryCustomerCrudRepository);
        PizzaRepository InMemoryPizzaRepository = new InMemoryPizzaRepository();
        pizzaService = new PizzaServiceImpl(InMemoryPizzaRepository);
        SupplySystemRepository inMemoryIngredientRepository = new InMemoryIngredientRepository();
        supplySystemCommandService = new SupplySystemCommandServiceImpl(inMemoryIngredientRepository);
        supplySystemQueryService = new SupplySystemQueryServiceImpl(inMemoryIngredientRepository);

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

        orderPricingService = new OrderPricingServiceImpl(discountCatalog);
        orderService = new OrderServiceImpl(inMemoryOrderRepository, pizzaService, supplySystemQueryService, supplySystemCommandService );
        objectUnderTest = new OperatorCommandServiceImpl(customerService, orderService, orderPricingService, pizzaService);

        inMemoryIngredientRepository.saveAll(IngredientsTestData.getAllIngredients());
        PizzaMenuTestData.getAllPizzas().forEach(pizzaService::addPizzaToMenu);
    }

    NewCustomerDto firstTestClient = NewCustomerDto.builder()
            .firstName("Anna")
            .lastName("Kowalska")
            .age(25)
            .isStudent(false)
            .email("anna.kowalska@gmail.com")
            .phoneNumber("987654321")
            .build();

    NewCustomerDto secondTestClient = NewCustomerDto.builder()
            .firstName("Jan")
            .lastName("Nowak")
            .age(19)
            .isStudent(true)
            .email("jan.nowak@gmail.com")
            .phoneNumber("456789123")
            .build();

    @Test
    @DisplayName("registerNewOrder should successfully register a new order")
    void registerNewOrder() {
//        given
        Customer testCustomer = customerService.registerNewCustomer(firstTestClient);
        List<Long> pizzaIds = List.of(1L, 2L, 3L);

        List<BigDecimal> pizzaPrices = pizzaIds.stream()
                .map(id -> pizzaService.getPizzaById(id))
                .map(Pizza::getPrice)
                .toList();
        BigDecimal totalPrice = orderPricingService.calculateOrderPrice(pizzaPrices, testCustomer);

        NewOrderDto testOrder = NewOrderDto.builder()
                .customerId(testCustomer.getId())
                .pizzaIdList(pizzaIds)
                .build();
//        when
        Order testResult = objectUnderTest.registerNewOrder(testOrder);
        System.out.println("testResult: " + testResult.getId());
//        then
        assertAll(
                () -> assertEquals(testResult.getPizzaIds(), testOrder.getPizzaIdList()),
                () -> assertEquals(totalPrice, testResult.getOrderValue()),
                () -> assertEquals(testOrder.getCustomerId(), testResult.getCustomerId())
        );
    }


    @DisplayName("deleteOrder should successfully delete order in NEW status")
    @Test
    void deleteOrder() {
        // Given
        Customer testCustomer = customerService.registerNewCustomer(firstTestClient);
        List<Long> pizzaIds = List.of(1L, 2L, 3L);

        NewOrderDto testOrder = NewOrderDto.builder()
                .customerId(testCustomer.getId())
                .pizzaIdList(pizzaIds)
                .build();
        // When
        Order testResult = objectUnderTest.registerNewOrder(testOrder);
        // then
        Order savedOrder = inMemoryOrderRepository.findById(testResult.getId());
        assertNotNull(savedOrder, "Saved order should not be null");
        assertEquals(testResult.getId(), savedOrder.getId(), "Order IDs should match");
        assertEquals(OrderStatus.NEW, savedOrder.getOrderStatus(), "Initial status should be NEW");
        // When deleting
        objectUnderTest.deleteOrder(testResult.getId());
        // Then
        assertThrows(
                OrderNotFoundException.class,
                () -> inMemoryOrderRepository.findById(testResult.getId()),
                "Order should not be found after deletion"
        );
    }
}