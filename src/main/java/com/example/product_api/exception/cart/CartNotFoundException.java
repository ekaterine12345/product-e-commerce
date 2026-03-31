package com.example.product_api.exception.cart;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String userId) {
        super("Cart not found for user: " + userId);
    }
}
