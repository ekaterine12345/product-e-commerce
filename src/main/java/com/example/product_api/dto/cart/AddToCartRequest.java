package com.example.product_api.dto.cart;

public record AddToCartRequest(
        Long productId,

        @jakarta.validation.constraints.Min(1)
        Integer quantity
) {}