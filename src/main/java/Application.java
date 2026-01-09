package org.example;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    public static void main(String[] args) {

        System.out.println("Bienvenue chez FoodFast !");
        System.out.println("----------------------------------------");

        DeliveryPlatform platform = new DeliveryPlatform();

        Customer c1 = new Customer("C001", "Alice Dupont", "12 Rue de Java");

        Dish d1 = new Dish("Pizza Reine", new BigDecimal("12.50"), DishSize.MEDIUM);
        Dish d2 = new Dish("Tiramisu", new BigDecimal("4.00"), DishSize.SMALL);

        Order order1 = new Order(c1);
        order1.addDish(d1, 2);
        order1.addDish(d2, 1);

        platform.placeOrder(order1);

        System.out.println("Prix total : " + order1.calculateTotalPrice() + " €");

        List<Order> preparingOrders =
                platform.findOrdersByStatus(OrderStatus.IN_PREPARATION);

        System.out.println("Commandes en préparation : " + preparingOrders.size());

        System.out.println("----------------------------------------");
        System.out.println("Simulation multi-threads");

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 3; i++) {
            int index = i;

            executor.submit(() -> {
                Customer customer =
                        new Customer("C" + index, "Client " + index, "Adresse " + index);

                Order order = new Order(customer);
                order.addDish(
                        new Dish("Burger", new BigDecimal("8.00"), DishSize.MEDIUM),
                        1
                );

                platform.placeOrder(order);
            });
        }

        executor.shutdown();
        System.out.println("Fin de l'application.");
    }
}
