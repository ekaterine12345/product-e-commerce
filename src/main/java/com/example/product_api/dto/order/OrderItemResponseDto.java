package com.example.product_api.dto.order;

public record OrderItemResponseDto(
        Long productId,
        String productName,
        Double priceAtPurchase,
        Integer quantity
) {}
