package org.example;

import db.DatabaseConfig;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Order {

    private String id = UUID.randomUUID().toString();
    private OrderStatus status;
    private Map<Dish, Integer> dishes;
    private Customer customer;
    private LocalDateTime orderDate;

    public Order(Customer customer) {
        this.status = OrderStatus.PENDING;
        this.dishes = new HashMap<>();
        this.customer = customer;
        this.orderDate = LocalDateTime.now();
    }

    public void addDish(Dish dish, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("La quantité doit être positive");
        }
        dishes.put(dish, quantity);
    }

    public BigDecimal calculateTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Dish, Integer> entry : dishes.entrySet()) {
            Dish dish = entry.getKey();
            int quantity = entry.getValue();
            total = total.add(
                    dish.getPrice().multiply(BigDecimal.valueOf(quantity))
            );
        }

        return total;
    }

    // =======================
    // BONUS – SAUVEGARDE DB
    // =======================
    public void saveToDatabase() {

        String sql = """
            INSERT INTO orders (id, customer_id, status, total_price, order_date)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, this.id);
            statement.setString(2, this.customer.getId());
            statement.setString(3, this.status.name());
            statement.setBigDecimal(4, this.calculateTotalPrice());
            statement.setTimestamp(5, Timestamp.valueOf(this.orderDate));

            statement.executeUpdate();
            System.out.println("✅ Order enregistrée dans PostgreSQL");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =======================
    // GETTERS & SETTERS
    // =======================
    public String getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Map<Dish, Integer> getDishes() {
        return dishes;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    // =======================
    // EQUALS / HASHCODE
    // =======================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // =======================
    // TO STRING
    // =======================
    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", customer=" + customer.getName() +
                ", orderDate=" + orderDate +
                '}';
    }
}
