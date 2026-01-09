package org.example;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testOrderStatusIsPendingByDefault() {
        Customer customer = new Customer("C1", "Alice", "Paris");
        Order order = new Order(customer);

        assertEquals(OrderStatus.PENDING, order.getStatus());
    }

    @Test
    void testCalculateTotalPrice() {
        Customer customer = new Customer("C1", "Alice", "Paris");
        Order order = new Order(customer);

        Dish pizza = new Dish("Pizza", new BigDecimal("10.00"), DishSize.MEDIUM);
        Dish burger = new Dish("Burger", new BigDecimal("5.00"), DishSize.SMALL);

        order.addDish(pizza, 2);   // 2 × 10 = 20
        order.addDish(burger, 1);  // 1 × 5 = 5

        assertEquals(new BigDecimal("25.00"), order.calculateTotalPrice());
    }

    @Test
    void testAddDishWithInvalidQuantityThrowsException() {
        Customer customer = new Customer("C1", "Alice", "Paris");
        Order order = new Order(customer);

        Dish pizza = new Dish("Pizza", new BigDecimal("10.00"), DishSize.MEDIUM);

        assertThrows(IllegalArgumentException.class, () -> {
            order.addDish(pizza, 0);
        });
    }

}
