package org.example;


import java.util.Random;

public class Restaurant {
    private final Random random = new Random();

    public void prepare(Order order) throws OrderPreparationException {
        if (this.random.nextDouble() < 0.2) {
            throw new OrderPreparationException("Erreur de cuisine : ingrédient manquant pour la commande " + order.getId());
        } else {
            System.out.println("Cuisine : Commande " + order.getId() + " préparée avec succès !");
            order.setStatus(OrderStatus.IN_PREPARATION);
        }
    }
}
