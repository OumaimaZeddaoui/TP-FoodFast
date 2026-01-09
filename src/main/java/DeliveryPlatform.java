package org.example;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import db.DatabaseConfig;

public class DeliveryPlatform {

    private final Map<String, Order> orders = new ConcurrentHashMap<>();
    private final Restaurant kitchen = new Restaurant();

    public void addOrder(Order order) {
        placeOrder(order);
    }

    public synchronized void placeOrder(Order order) {

        orders.put(order.getId(), order);
        System.out.println("Plateforme : Commande " + order.getId() + " enregistrée.");

        try {
            kitchen.prepare(order);

            if (order.getStatus() == OrderStatus.PENDING) {
                order.setStatus(OrderStatus.IN_PREPARATION);
            }

            if (order.getStatus() != OrderStatus.CANCELLED) {
                saveOrder(order);
            }

        } catch (OrderPreparationException e) {
            System.err.println("ERREUR DE PRÉPARATION : " + e.getMessage());
            order.setStatus(OrderStatus.CANCELLED);
            System.out.println("Plateforme : Commande " + order.getId() + " annulée.");
        }
    }

    public Optional<Order> findOrderById(String orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    public List<Order> findOrdersByCustomer(Customer customer) {
        return orders.values()
                .stream()
                .filter(order -> order.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }

    public List<Order> findOrdersByStatus(OrderStatus status) {
        return orders.values()
                .stream()
                .filter(order -> order.getStatus() == status)
                .collect(Collectors.toList());
    }

    public void saveOrder(Order order) {

        String sql = "INSERT INTO orders (id, customer_id, status, total_price, order_date) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, order.getId());
            ps.setString(2, order.getCustomer().getId());
            ps.setString(3, order.getStatus().name());
            ps.setBigDecimal(4, order.calculateTotalPrice());
            ps.setTimestamp(5, Timestamp.valueOf(order.getOrderDate()));

            ps.executeUpdate();
            System.out.println("Commande " + order.getId() + " sauvegardée en base.");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }
}
