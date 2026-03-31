package com.example.product_api.dto.cart;

public record CartItemResponseDto(
        Long productId,
        String productName,
        Double price,
        Integer quantity
) {}
