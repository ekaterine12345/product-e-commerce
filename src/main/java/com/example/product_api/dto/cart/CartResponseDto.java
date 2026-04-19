package com.example.product_api.dto.cart;

import java.util.List;

public record CartResponseDto(
        Long cartId,
        List<CartItemResponseDto> items
) {} // TODO: total price
