package com.example.product_api.mapper;

import com.example.product_api.dto.cart.CartItemResponseDto;
import com.example.product_api.dto.cart.CartResponseDto;
import com.example.product_api.dto.order.OrderItemResponseDto;
import com.example.product_api.dto.order.OrderResponseDto;
import com.example.product_api.entity.Cart;
import com.example.product_api.entity.CartItem;
import com.example.product_api.entity.Order;
import com.example.product_api.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "id", target = "orderId")
    List<OrderResponseDto> toDtoList(List<Order> orders);

    @Mapping(source = "id", target = "orderId")
    @Mapping(target = "items", source = "items")
    OrderResponseDto toDto(Order order);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    OrderItemResponseDto toItemDto(OrderItem item);

    List<OrderItemResponseDto> toItemDtoList(List<OrderItem> items);

}
