package com.example.product_api.controller.business;

import com.example.product_api.dto.ApiResponse;
import com.example.product_api.service.business.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public ResponseEntity<ApiResponse> getCart(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(new ApiResponse("User cart", cartService.getCart(jwt.getSubject())));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ApiResponse> addToCart(@AuthenticationPrincipal Jwt jwt,
                                                 @RequestParam Long productId,
                                                 @RequestParam Integer quantity) {
        // TODO: refactor return type in  cartService.addToCar
        return ResponseEntity.ok(new ApiResponse("User cart", cartService.addToCart(jwt.getSubject(), productId, quantity ) ));
    }


    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/items")
    public void remove(@RequestParam Long productId, @AuthenticationPrincipal Jwt jwt) {
        cartService.removeFromCart(jwt.getSubject(), productId);
    }

}
