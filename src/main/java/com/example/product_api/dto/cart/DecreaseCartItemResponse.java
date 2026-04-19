package com.example.product_api.dto.cart;

public record DecreaseCartItemResponse(
        int requested,
        int applied,
        boolean removed
) {}