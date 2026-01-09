package org.example;
import java.math.BigDecimal;
import java.util.Objects;

public class Dish {
    private String name;
    private BigDecimal price;
    private DishSize size;

    public Dish(String name, BigDecimal price, DishSize size) {
        this.name = name;
        this.price = price != null ? price : BigDecimal.ZERO;
        this.size = size;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public DishSize getSize() {
        return this.size;
    }

    public String toString() {
        String var10000 = this.name;
        return "Dish{name='" + var10000 + "', price=" + String.valueOf(this.price) + ", size=" + String.valueOf(this.size) + "}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Dish dish = (Dish)o;
            return Objects.equals(this.name, dish.name) && Objects.equals(this.price, dish.price) && this.size == dish.size;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.name, this.price, this.size});
    }
}
