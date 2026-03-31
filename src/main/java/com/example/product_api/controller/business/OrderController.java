package com.example.product_api.controller.business;

import com.example.product_api.dto.ApiResponse;
import com.example.product_api.service.business.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse> checkout(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(
                new ApiResponse("Order created", orderService.checkout(jwt.getSubject()))
        );
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse> myOrders(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(
                new ApiResponse("My orders", orderService.getUserOrders(jwt.getSubject()))
        );
    }
}
