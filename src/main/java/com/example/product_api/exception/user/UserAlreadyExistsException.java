package com.example.product_api.exception.user;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username, String email) {
        super("User already exists: " + username + " or " + email + " already is use");
    }
}
