package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryPlatformTest {

    @Test
    void testAddAndFindOrderById() {
        DeliveryPlatform platform = new DeliveryPlatform();

        Customer customer = new Customer("C1", "Alice", "Paris");
        Order order = new Order(customer);

        platform.addOrder(order);

        assertTrue(platform.findOrderById(order.getId()).isPresent());
    }

    @Test
    void testFindOrdersByCustomer() {

        DeliveryPlatform platform = new DeliveryPlatform();

        Customer alice = new Customer("C1", "Alice", "Paris");
        Customer bob = new Customer("C2", "Bob", "Lyon");

        Order order1 = new Order(alice);
        Order order2 = new Order(alice);
        Order order3 = new Order(bob);

        platform.addOrder(order1);
        platform.addOrder(order2);
        platform.addOrder(order3);

        // Alice a 2 commandes
        assertEquals(2, platform.findOrdersByCustomer(alice).size());

        // Bob a 1 commande
        assertEquals(1, platform.findOrdersByCustomer(bob).size());
    }

    @Test
    void testFindOrdersByStatus() {

        DeliveryPlatform platform = new DeliveryPlatform();

        Customer alice = new Customer("C1", "Alice", "Paris");

        Order order1 = new Order(alice);
        Order order2 = new Order(alice);
        Order order3 = new Order(alice);

        platform.addOrder(order1);
        platform.addOrder(order2);
        platform.addOrder(order3);

        order1.setStatus(OrderStatus.PENDING);
        order2.setStatus(OrderStatus.IN_PREPARATION);
        order3.setStatus(OrderStatus.IN_PREPARATION);

        assertEquals(1, platform.findOrdersByStatus(OrderStatus.PENDING).size());
        assertEquals(2, platform.findOrdersByStatus(OrderStatus.IN_PREPARATION).size());
    }

}
