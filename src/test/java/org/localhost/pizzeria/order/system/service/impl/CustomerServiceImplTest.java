package org.localhost.pizzeria.order.system.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.localhost.pizzeria.order.system.OrderStatus;
import org.localhost.pizzeria.order.system.dto.NewCustomerDto;
import org.localhost.pizzeria.order.system.dto.UpdateCustomerDataDto;
import org.localhost.pizzeria.order.system.exceptions.CustomerEmailNotUniqueException;
import org.localhost.pizzeria.order.system.exceptions.CustomerNotFoundException;
import org.localhost.pizzeria.order.system.exceptions.CustomerPhoneNumberNotUniqueException;
import org.localhost.pizzeria.order.system.exceptions.OrderExceptionsMessages;
import org.localhost.pizzeria.order.system.model.Customer;
import org.localhost.pizzeria.order.system.model.Order;
import org.localhost.pizzeria.order.system.repository.CustomerRepository;
import org.localhost.pizzeria.order.system.service.CustomerService;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceImplTest {

    private CustomerService objectUnderTest;
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository = new InMemoryCustomerRepository();
        objectUnderTest = new CustomerServiceImpl(customerRepository);
    }

    private NewCustomerDto newCustomerDto = NewCustomerDto.builder().firstName("test name").lastName("test lastname").email("testEmail@test.com").phoneNumber("+48111222333").address("test address").build();

    private UpdateCustomerDataDto updateCustomerDataDto = UpdateCustomerDataDto.builder().id(1).email("updateEmail@mail.com").phoneNumber("+48444000999").address("updated address").build();

    @DisplayName("findCustomerByEmail should return registered user")
    @Test
    void findCustomerByEmail() {
//        given
        Customer testCustomer = objectUnderTest.registerNewCustomer(newCustomerDto);
//        when
        Customer foundCustomer = objectUnderTest.findCustomerByEmail(testCustomer.getEmail());
//        then
        assertAll(() -> assertEquals(testCustomer.getId(), foundCustomer.getId()), () -> assertEquals(testCustomer.getFirstName(), foundCustomer.getFirstName()), () -> assertEquals(testCustomer.getLastName(), foundCustomer.getLastName()), () -> assertEquals(testCustomer.getEmail(), foundCustomer.getEmail()), () -> assertEquals(testCustomer.getPhoneNumber(), foundCustomer.getPhoneNumber()));
    }

    @Test
    @DisplayName("findCustomerByPhoneNumber should return registered user")
    void findCustomerByPhoneNumber() {
        //        given
        Customer testCustomer = objectUnderTest.registerNewCustomer(newCustomerDto);
//        when
        Customer foundCustomer = objectUnderTest.findCustomerByPhoneNumber(testCustomer.getPhoneNumber());
//        then
        assertAll(() -> assertEquals(testCustomer.getId(), foundCustomer.getId()), () -> assertEquals(testCustomer.getFirstName(), foundCustomer.getFirstName()), () -> assertEquals(testCustomer.getLastName(), foundCustomer.getLastName()), () -> assertEquals(testCustomer.getEmail(), foundCustomer.getEmail()), () -> assertEquals(testCustomer.getPhoneNumber(), foundCustomer.getPhoneNumber()));
    }

    @DisplayName("findCustomerByEmail should throw when customer not found")
    @Test
    void findCustomerByEmailNotFound() {
//        given
        String NON_EXISTING_CUSTOMER_EMAIL = "testEmail@test.com";
//        when, then
        CustomerNotFoundException testResult = assertThrows(CustomerNotFoundException.class, () -> objectUnderTest.findCustomerByEmail(NON_EXISTING_CUSTOMER_EMAIL));

        assertAll(() -> assertEquals(testResult.getMessage(), OrderExceptionsMessages.CUSTOMER_NOT_FOUND.getErrorMessage()), () -> assertEquals(testResult.getErrorCode(), OrderExceptionsMessages.CUSTOMER_NOT_FOUND.getErrorCode()));
    }


    @DisplayName("findCustomerByPhoneNumber should throw when customer not found")
    @Test
    void findCustomerByPhoneNumberNotFound() {
//        given
        String NON_EXISTING_CUSTOMER_PHONE_NUMBER = "+48555000999";

//        when, then
        CustomerNotFoundException testResult = assertThrows(CustomerNotFoundException.class, () -> objectUnderTest.findCustomerByPhoneNumber(NON_EXISTING_CUSTOMER_PHONE_NUMBER));

        assertAll(() -> assertEquals(testResult.getMessage(), OrderExceptionsMessages.CUSTOMER_NOT_FOUND.getErrorMessage()), () -> assertEquals(testResult.getErrorCode(), OrderExceptionsMessages.CUSTOMER_NOT_FOUND.getErrorCode()));
    }

    @DisplayName("findCustomerByPhoneNumber should throw when phone number is null")
    @Test
    void findCustomerByPhoneNumberNullPhoneNumber() {
//        given
        String NON_EXISTING_CUSTOMER_PHONE_NUMBER = null;
//        when
        IllegalArgumentException testResult = assertThrows(
                IllegalArgumentException.class,
                () -> objectUnderTest.findCustomerByPhoneNumber(NON_EXISTING_CUSTOMER_PHONE_NUMBER)
        );
    }

    @DisplayName("registerNewCustomer should successfully register new customer")
    @Test
    void registerNewCustomer() {
//        given, when
        Customer testResult = objectUnderTest.registerNewCustomer(newCustomerDto);
//        then
        assertAll(() -> assertEquals(testResult.getFirstName(), newCustomerDto.getFirstName()), () -> assertEquals(testResult.getLastName(), newCustomerDto.getLastName()), () -> assertEquals(testResult.getAddress(), newCustomerDto.getAddress()), () -> assertEquals(testResult.getEmail(), newCustomerDto.getEmail()), () -> assertEquals(testResult.getPhoneNumber(), newCustomerDto.getPhoneNumber()));
    }

    @DisplayName("registerNewCustomer should throw when email is not unique")
    @Test
    void registerNewCustomer_shouldThrowWhenEmailIsNotUnique() {
//        given
        objectUnderTest.registerNewCustomer(newCustomerDto);
        NewCustomerDto notUniqueEmailCustomer = NewCustomerDto.builder().firstName("test name").lastName("test lastname").email("testEmail@test.com").phoneNumber("+48111222333").address("test address").build();
//        when, then
        CustomerEmailNotUniqueException testResult = assertThrows(CustomerEmailNotUniqueException.class, () -> objectUnderTest.registerNewCustomer(notUniqueEmailCustomer));

        assertAll(() -> assertEquals(OrderExceptionsMessages.CUSTOMER_EMAIL_NOT_UNIQUE.getErrorCode(), testResult.getErrorCode()), () -> assertEquals(OrderExceptionsMessages.CUSTOMER_EMAIL_NOT_UNIQUE.getErrorMessage(), testResult.getMessage()));
    }

    @DisplayName("registerNewCustomer should throw when phone number is not unique")
    @Test
    void registerNewCustomer_shouldThrowWhenPhoneNumberIsNotUnique() {
//        given
        objectUnderTest.registerNewCustomer(newCustomerDto);
        NewCustomerDto notUniquePhoneNumberCustomer = NewCustomerDto.builder().firstName("test name").lastName("test lastname").email("uniqueTestEmail@test.com").phoneNumber("+48111222333").address("test address").build();
//        when, then
        CustomerPhoneNumberNotUniqueException testResult = assertThrows(CustomerPhoneNumberNotUniqueException.class, () -> objectUnderTest.registerNewCustomer(notUniquePhoneNumberCustomer));

        assertAll(() -> assertEquals(OrderExceptionsMessages.CUSTOMER_PHONE_NUMBER_NOT_UNIQUE.getErrorCode(), testResult.getErrorCode()), () -> assertEquals(OrderExceptionsMessages.CUSTOMER_PHONE_NUMBER_NOT_UNIQUE.getErrorMessage(), testResult.getMessage()));
    }

    @DisplayName("deleteCustomer should successfully delete customer")
    @Test
    void deleteCustomer() {
//        given
        Customer testCustomer = objectUnderTest.registerNewCustomer(newCustomerDto);
//        when
        long deletedCustomerId = objectUnderTest.deleteCustomer(testCustomer.getId());
//        then
        assertEquals(testCustomer.getId(), deletedCustomerId);
    }

    @DisplayName("deleteCustomer should throw when no customer found")
    @Test
    void deleteCustomer_shouldThrowWhenNoCustomerFound() {
        long NON_EXISTING_CUSTOMER_ID = 11L;
//        when, then
        CustomerNotFoundException testResult = assertThrows(CustomerNotFoundException.class, () -> objectUnderTest.deleteCustomer(NON_EXISTING_CUSTOMER_ID));
        assertAll(() -> assertEquals(testResult.getMessage(), OrderExceptionsMessages.CUSTOMER_NOT_FOUND.getErrorMessage()), () -> assertEquals(testResult.getErrorCode(), OrderExceptionsMessages.CUSTOMER_NOT_FOUND.getErrorCode()));
    }

    @DisplayName("updateCustomerData should update customer data")
    @Test
    void updateCustomerData() {
//        given
        Customer testCustomer = objectUnderTest.registerNewCustomer(newCustomerDto);
//        when
        Customer testResult = objectUnderTest.updateCustomerData(updateCustomerDataDto);
//        then
        assertAll(() -> assertEquals(testCustomer.getId(), testResult.getId()), () -> assertEquals(testCustomer.getEmail(), testResult.getEmail()), () -> assertEquals(testCustomer.getPhoneNumber(), testResult.getPhoneNumber()), () -> assertEquals(testCustomer.getAddress(), testResult.getAddress()));
    }

    @DisplayName("updateCustomerData should throw when customer not found")
    @Test
    void updateCustomerData_shouldThrowWhenCustomerNotFound() {
//        when, then
        CustomerNotFoundException testResult = assertThrows(CustomerNotFoundException.class, () -> objectUnderTest.updateCustomerData(updateCustomerDataDto));

        assertAll(() -> assertEquals(testResult.getMessage(), OrderExceptionsMessages.CUSTOMER_NOT_FOUND.getErrorMessage()), () -> assertEquals(testResult.getErrorCode(), OrderExceptionsMessages.CUSTOMER_NOT_FOUND.getErrorCode()));
    }

    @DisplayName("updateCustomerOrder should update customer orders")
    @Test
    void updateCustomerOrder() {
//        given
        List<Long> pizzaIds = List.of(1l,2l);
        Customer testCustomer = objectUnderTest.registerNewCustomer(newCustomerDto);
        Order newOrder = Order.builder()
                .customer(testCustomer)
                .orderStatus(OrderStatus.NEW)
                .orderReceivedDate(ZonedDateTime.now())
                .pizzaIds(pizzaIds)
                .build();
        int amountOfOrdersBeforeUpdate = objectUnderTest.getOrdersByCustomerId(testCustomer.getId()).size();
        int EXPECTED_NUMBER_OF_ORDERS_BEFORE_UPDATE = 0;
//    when
        objectUnderTest.updateCustomerOrder(testCustomer.getId(), newOrder);
        int testOrdersAfterUpdate = objectUnderTest.getOrdersByCustomerId(testCustomer.getId()).size();
        int EXPECTED_NUMBER_OF_ORDERS_AFTER_UPDATE = EXPECTED_NUMBER_OF_ORDERS_BEFORE_UPDATE + 1;
//        then
        assertAll(
                () -> assertNotEquals(amountOfOrdersBeforeUpdate, testOrdersAfterUpdate),
                () -> assertEquals(EXPECTED_NUMBER_OF_ORDERS_BEFORE_UPDATE, amountOfOrdersBeforeUpdate),
                () -> assertEquals(EXPECTED_NUMBER_OF_ORDERS_AFTER_UPDATE, testOrdersAfterUpdate)
        );
    }

    @DisplayName("getOrdersByCustomerId should return empty list of orders")
    @Test
    void getOrdersByCustomerId() {
//        given
        int RESULT_ORDER_LIST_SIZE = 0;
        Customer testCustomer = objectUnderTest.registerNewCustomer(newCustomerDto);
//        when
        List<Order> testResult = objectUnderTest.getOrdersByCustomerId(testCustomer.getId());
//        then
        assertEquals(RESULT_ORDER_LIST_SIZE, testResult.size());
    }

    @DisplayName("getOrdersByCustomerId should throw when customer not found")
    @Test
    void getOrdersByCustomerId_shouldThrowWhenCustomerNotFound() {
//        given
        int NON_EXISTING_CUSTOMER_ID = 11;
//        when
        CustomerNotFoundException testResult = assertThrows(CustomerNotFoundException.class, () -> objectUnderTest.getOrdersByCustomerId(NON_EXISTING_CUSTOMER_ID));

//        then
        assertAll(() -> assertEquals(testResult.getMessage(), OrderExceptionsMessages.CUSTOMER_NOT_FOUND.getErrorMessage()), () -> assertEquals(testResult.getErrorCode(), OrderExceptionsMessages.CUSTOMER_NOT_FOUND.getErrorCode()));
    }

    @DisplayName("getOrdersByCustomerEmail should return empty list")
    @Test
    void getOrdersByCustomerEmail() {
        //        given
        int RESULT_ORDER_LIST_SIZE = 0;
        Customer testCustomer = objectUnderTest.registerNewCustomer(newCustomerDto);
//        when
        List<Order> testResult = objectUnderTest.getOrdersByCustomerEmail(testCustomer.getEmail());
//        then
        assertEquals(RESULT_ORDER_LIST_SIZE, testResult.size());
    }

    @DisplayName("getOrdersByCustomerEmail should throw when no email found")
    @Test
    void getOrdersByCustomerEmail_shouldThrowWhenNoEmailFound() {
        //        given
        String NON_EXISTING_CUSTOMER_EMAIL = "notexist@gmail.com";
//        when
        CustomerNotFoundException testResult = assertThrows(CustomerNotFoundException.class, () -> objectUnderTest.getOrdersByCustomerEmail(NON_EXISTING_CUSTOMER_EMAIL));

//        then
        assertAll(() -> assertEquals(testResult.getMessage(), OrderExceptionsMessages.CUSTOMER_NOT_FOUND.getErrorMessage()), () -> assertEquals(testResult.getErrorCode(), OrderExceptionsMessages.CUSTOMER_NOT_FOUND.getErrorCode()));
    }

    @DisplayName("getOrdersByCustomerEmail should return empty list")
    @Test
    void getOrdersByCustomerPhoneNumber() {
        //        given
        int RESULT_ORDER_LIST_SIZE = 0;
        Customer testCustomer = objectUnderTest.registerNewCustomer(newCustomerDto);
//        when
        List<Order> testResult = objectUnderTest.getOrdersByCustomerPhoneNumber(testCustomer.getPhoneNumber());
//        then
        assertEquals(RESULT_ORDER_LIST_SIZE, testResult.size());
    }


    @DisplayName("getOrdersByCustomerPhoneNumber should throw when no phone number found")
    @Test
    void getOrdersByCustomerPhoneNumber_shouldThrowWhenNoPhoneNumberFound() {
        //        given
        String NON_EXISTING_CUSTOMER_PHONE = "+55111000999";
//        when
        CustomerNotFoundException testResult = assertThrows(CustomerNotFoundException.class, () -> objectUnderTest.getOrdersByCustomerPhoneNumber(NON_EXISTING_CUSTOMER_PHONE));

//        then
        assertAll(() -> assertEquals(testResult.getMessage(), OrderExceptionsMessages.CUSTOMER_NOT_FOUND.getErrorMessage()), () -> assertEquals(testResult.getErrorCode(), OrderExceptionsMessages.CUSTOMER_NOT_FOUND.getErrorCode()));
    }
}