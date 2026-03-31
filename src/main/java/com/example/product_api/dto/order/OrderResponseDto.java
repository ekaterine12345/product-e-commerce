package com.example.product_api.dto.order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long orderId,
        LocalDateTime orderDate,
        List<OrderItemResponseDto> items
) {}
