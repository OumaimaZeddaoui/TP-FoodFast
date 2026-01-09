# Delivery Platform – Java Project

## Description
Ce projet est une application Java simulant une plateforme de livraison de repas.
Il a été réalisé dans le cadre des séances de travaux pratiques.

Le projet met en œuvre les concepts suivants :
- Programmation orientée objet
- Collections Java
- Gestion des exceptions
- Tests unitaires avec JUnit
- Introduction à JDBC (PostgreSQL)

## Fonctionnalités implémentées
- Ajout d’une commande à la plateforme
- Recherche d’une commande par son identifiant
- Recherche des commandes d’un client
- Recherche des commandes par statut
- Gestion des erreurs lors de la préparation des commandes
- Tests unitaires validant le comportement attendu

## Tests unitaires
Les tests unitaires sont écrits avec **JUnit 5**.

Ils fonctionnent **sans base de données**.
La connexion PostgreSQL n’est pas requise pour exécuter les tests.

Les messages d’erreur liés à PostgreSQL peuvent apparaître dans la console,
mais ils n’empêchent pas la réussite des tests.

## Base de données (PostgreSQL)
Une connexion PostgreSQL est prévue dans le projet via JDBC.

Elle est optionnelle et utilisée uniquement pour illustrer
la sauvegarde des commandes en base de données.

Configuration attendue :
- Base : foodfast
- Port : 5432
- URL : jdbc:postgresql://localhost:5432/foodfast

## Technologies utilisées
- Java 21
- Maven
- JUnit 5
- PostgreSQL (optionnel)

## Lancer les tests
Commande Maven :
```bash
mvn test
