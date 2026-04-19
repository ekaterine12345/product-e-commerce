package com.example.product_api.service.business;

import com.example.product_api.dto.cart.DecreaseCartItemResponse;
import com.example.product_api.exception.cart.ItemNotInCartException;
import com.example.product_api.dto.cart.CartItemResponseDto;
import com.example.product_api.dto.cart.CartResponseDto;
import com.example.product_api.entity.Cart;
import com.example.product_api.entity.CartItem;
import com.example.product_api.entity.Product;
import com.example.product_api.mapper.CartMapper;
import com.example.product_api.repository.CartItemRepository;
import com.example.product_api.repository.CartRepository;
import com.example.product_api.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
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

    private Cart getOrCreateCart(String userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUserId(userId);
                    return cartRepository.save(cart);
                });
    }

    private CartItem getCartItem(Cart cart, Long productId) {
        return cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new ItemNotInCartException("Product with id " + productId + " not in cart"));
    }

    public CartItemResponseDto addToCart(String userId, Long productId, Integer quantity) {
        Cart cart = getOrCreateCart(userId);

        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElse(null);

        if (item != null) {
            item.increase(quantity);
        } else {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + productId ));

            item = new CartItem(cart, product, quantity);

            cart.getItems().add(item);
        }

        return cartMapper.toItemDto(item);
    }

    public DecreaseCartItemResponse decreaseItemQuantity(String userId, Long productId, int amount) {
        Cart cart = getOrCreateCart(userId);
        CartItem item = getCartItem(cart, productId);

        int currentQty = item.getQuantity();

        if (amount >= currentQty) {
            cart.getItems().remove(item);
            return new DecreaseCartItemResponse(amount, currentQty, true);
        }

        item.decrease(amount);
        return new DecreaseCartItemResponse(amount, amount, false);
    }


    public void removeFromCart(String userId, Long productId) {
        Cart cart = getOrCreateCart(userId);

        CartItem item =  getCartItem(cart, productId);

        cart.getItems().remove(item);
    }

    public void clearCart(String userId) {
        Cart cart = getOrCreateCart(userId);

        if (cart.getItems().isEmpty()) {
            return;
        }

        cart.getItems().clear();
    }


    public CartResponseDto getCart(String userId) {
        return cartMapper.toDto(getOrCreateCart(userId));
    }
}
