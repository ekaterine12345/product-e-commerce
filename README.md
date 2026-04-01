# E-commerce

## Overview
This is an e-commerce back-end side written using Java 25 and Spring Boot 4. Users can register to the website and sing in. Signed In users can add or delete items to cart, see item in cart with their coresponding quantity,
order items from cart, and view orders.

## User types:
* USER - the general user which registers to the system; able to see and purchase products;
* ADMIN - the root built-in user, able to view, add, update and delete products; Can add EDITORs to system; 
* EDITOR - low-profile admin, able to view, add and update product, but can not delete; after root admin adds editor user manually,
   editor gets link as an email notofication to activate account and set password.

## Used Technologies:
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
* Validations

## Coming Soon
* Payment Integration
* Inventory Microservice
* Better Documentation (scheme DB images, user flow, endpoints)
