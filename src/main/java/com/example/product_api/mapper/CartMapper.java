package com.example.product_api.mapper;

import com.example.product_api.dto.cart.CartItemResponseDto;
import com.example.product_api.dto.cart.CartResponseDto;
import com.example.product_api.entity.Cart;
import com.example.product_api.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "id", target = "cartId")
    CartResponseDto toDto(Cart cart);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "price")
    CartItemResponseDto toItemDto(CartItem item);

    List<CartItemResponseDto> toItemDtoList(List<CartItem> items);
}
