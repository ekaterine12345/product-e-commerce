package com.example.product_api.exception.user;

public class KeycloakUserCreationException extends RuntimeException {
    public KeycloakUserCreationException(String message) {
        super(message);
    }
}
