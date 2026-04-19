package com.example.product_api.exception.cart;

public class ItemNotInCartException extends RuntimeException {

    public ItemNotInCartException(String message) {
        super(message);
    }
}
