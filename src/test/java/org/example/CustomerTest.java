package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.example.Customer;

public class CustomerTest {
    @Test
    void testgetter(){
        Customer customer = new Customer("C1", "Alice", "Paris");

        assertEquals("Alice", customer.getName());
    }
}
