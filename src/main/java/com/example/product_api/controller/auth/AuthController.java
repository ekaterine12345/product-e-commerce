package com.example.product_api.controller.auth;

import com.example.product_api.dto.auth.LoginRequest;
import com.example.product_api.dto.auth.RegisterRequest;
import com.example.product_api.dto.auth.TokenResponse;
import com.example.product_api.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {

        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }

    // 1. TODO: credit card integration * later
    // 2. TODO: მარაგების ნახვა, არის თუ არა არსებული პროდუქტი მარაგში, ადმინი (მაინორ ადმინი?) ამატებდეს მარაგებს - microservices
    // 3. TODO: admin can attach rols: user => user + editor or editor => editor + user


}
