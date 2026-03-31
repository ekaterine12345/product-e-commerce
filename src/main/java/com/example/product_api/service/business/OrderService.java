package com.example.product_api.service.business;

import com.example.product_api.dto.order.OrderResponseDto;
import com.example.product_api.entity.Cart;
import com.example.product_api.entity.CartItem;
import com.example.product_api.entity.Order;
import com.example.product_api.entity.OrderItem;
import com.example.product_api.exception.cart.CartNotFoundException;
import com.example.product_api.exception.cart.EmptyCartException;
import com.example.product_api.mapper.OrderMapper;
import com.example.product_api.repository.CartRepository;
import com.example.product_api.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderResponseDto checkout(String userId) {
        Cart cart = getValidatedCart(userId);
        Order order = createOrder(userId);
        List<OrderItem> items = mapCartItems(cart, order);

        order.setItems(items);
        Order savedOrder = orderRepository.save(order);

        clearCart(cart);

        return orderMapper.toDto(savedOrder)  ;
    }

    private Cart getValidatedCart(String userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException(userId));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new EmptyCartException(userId);
        }

        return cart;
    }

    private Order createOrder(String userId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    private List<OrderItem> mapCartItems(Cart cart, Order order) {
        return cart.getItems().stream()
                .map(cartItem -> createOrderItem(cartItem, order))
                .toList();
    }

    private OrderItem createOrderItem(CartItem cartItem, Order order) {
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(cartItem.getProduct());
        item.setQuantity(cartItem.getQuantity());
        item.setPriceAtPurchase(cartItem.getProduct().getPrice());
        return item;
    }

    private void clearCart(Cart cart) {
        cart.getItems().clear();
    }

    public List<OrderResponseDto> getUserOrders(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orderMapper.toDtoList(orders);
    }
}
