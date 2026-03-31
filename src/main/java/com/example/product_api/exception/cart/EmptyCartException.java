package com.example.product_api.exception.cart;

public class EmptyCartException extends RuntimeException {
    public EmptyCartException(String userId) {
        super("Cart is empty for user: " + userId);
    }
}
