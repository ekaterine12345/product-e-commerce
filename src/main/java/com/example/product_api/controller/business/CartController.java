package com.example.product_api.controller.business;

import com.example.product_api.dto.ApiResponse;
import com.example.product_api.dto.cart.AddToCartRequest;
import com.example.product_api.dto.cart.DecreaseCartItemResponse;
import com.example.product_api.service.business.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
                                                 @Valid @RequestBody AddToCartRequest request) {
        return ResponseEntity.ok(new ApiResponse("User cart",
                cartService.addToCart(jwt.getSubject(), request.productId(), request.quantity() ) ));
    }


    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> remove(@PathVariable Long productId, @AuthenticationPrincipal Jwt jwt) {
        cartService.removeFromCart(jwt.getSubject(), productId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/items/{productId}/decrease")
    public ResponseEntity<ApiResponse> decreaseItem(@PathVariable Long productId,
            @RequestParam(defaultValue = "1") int amount,
            @AuthenticationPrincipal Jwt jwt) {
        DecreaseCartItemResponse result =
                cartService.decreaseItemQuantity(jwt.getSubject(), productId, amount);
        return ResponseEntity.ok(new ApiResponse("Item quantity updated", result));
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal Jwt jwt) {
        cartService.clearCart(jwt.getSubject());
        return ResponseEntity.noContent().build();
    }

}
