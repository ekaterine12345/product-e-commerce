package com.example.product_api.service.business;

import com.example.product_api.dto.cart.CartItemResponseDto;
import com.example.product_api.dto.cart.CartResponseDto;
import com.example.product_api.entity.Cart;
import com.example.product_api.entity.CartItem;
import com.example.product_api.entity.Product;
import com.example.product_api.mapper.CartMapper;
import com.example.product_api.repository.CartItemRepository;
import com.example.product_api.repository.CartRepository;
import com.example.product_api.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public Cart getOrCreateCart(String userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUserId(userId);
                    return cartRepository.save(cart);
                });
    }

    public CartItemResponseDto addToCart(String userId, Long productId, Integer quantity) {
        Cart cart = getOrCreateCart(userId);

        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            Product product = productRepository.findById(productId).orElseThrow();

            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);

            cart.getItems().add(newItem);
        } //  todo: return newly added product with quantity

        return cartMapper.toItemDto(cartItemRepository.findByCartIdAndProductId(cart.getId(), productId).orElse(null));
        // todo: return something like item != null ? item : newItem
    }

    public void removeFromCart(String userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow();

        cart.getItems().remove(item); // TODO: decrease amount
    }


    public CartResponseDto getCart(String userId) {
        return cartMapper.toDto(getOrCreateCart(userId));
    }
}
