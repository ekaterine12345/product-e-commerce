# E-commerce

## Overview
This project is a backend service for an e-commerce platform, built with Java 25 and Spring Boot 4.

It provides core functionality for managing users, products, carts, and orders. The system supports authentication and role-based access control, allowing different user types to perform specific actions within the platform.

## Key Features
* User registration and authentication
* Role-based authorization (USER, ADMIN, EDITOR)
* Product management
* Shopping cart functionality
* Order creation and tracking

## User Roles:
### USER
* Registers and logs into the system
* Browses products
* Adds/removes items from cart
* Views cart with quantities
* Places orders
* Views order history
### ADMIN 
* Full system access
* Manage products (create, update, delete)
* Manage users
* Can create EDITOR accounts
### EDITOR
* Limited administrative privileges:
   *  View products
   * Add new products
   * Update existing products
* Cannot delete products
* Receives an email invitation to activate their account and set a password

## Tech Stack:
* Java 25
* Spring Boot 4.0.3
* Gradle (Groovy)
* KeyCloak
* Spring Data JPA
* Spring Security
* Jackson
* Lombok
* Mapstruct
* PostgreSQL
* Docker
* Validation

## Getting Started
### Prerequisites
* Java 25
* Docker & Docker Compose
* PostgreSQL (if running locally without Docker)

### Run with Docker
``` docker-compose up --build ```

### Run locally
``` ./gradlew bootRun ```

## Future Improvements
* Payment Integration
* Inventory Microservice
* Database schema diagrams
* User flow documentation
* API documentation (Swagger/OpenAPI improvements)
