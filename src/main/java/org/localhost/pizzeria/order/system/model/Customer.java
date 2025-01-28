package org.localhost.pizzeria.order.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.localhost.pizzeria.order.system.OrderStatus;
import org.localhost.pizzeria.order.system.dto.NewCustomerDto;
import org.localhost.pizzeria.order.system.dto.UpdateCustomerDataDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers", schema = "ordering_system")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is mandatory")
    @NotNull
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @NotNull
    private String lastName;

    @Min(value = 5, message = "Client age must be greater than 5")
    private int age;

    boolean isStudent = false;

    @NotBlank(message = "customer address is mandatory")
    @NotNull
    private String address;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email is not valid")
    @NotNull
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    @NotNull
    private String phoneNumber;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Customer(Long id, String firstName, String lastName, int age, boolean isStudent,
                    String address, String email, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.isStudent = isStudent;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.orders = new ArrayList<>();
    }
    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        if (orders.contains(order) && order.getOrderStatus() == OrderStatus.NEW) {
            orders.remove(order);
        }
    }


    public static Customer fromNewCustomerDto(NewCustomerDto dto) {
        return Customer.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .age(dto.getAge())
                .isStudent(dto.isStudent())
                .address(dto.getAddress())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }

}
