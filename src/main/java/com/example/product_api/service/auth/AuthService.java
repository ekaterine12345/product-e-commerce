package com.example.product_api.service.auth;

import com.example.product_api.config.KeycloakProperties;
import com.example.product_api.dto.auth.LoginRequest;
import com.example.product_api.dto.auth.RegisterRequest;
import com.example.product_api.dto.auth.TokenResponse;
import com.example.product_api.enums.Role;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final KeycloakClient keycloakClient;
    private final KeycloakProperties properties;

    public AuthService(KeycloakClient keycloakClient, KeycloakProperties properties) {
        this.keycloakClient = keycloakClient;
        this.properties = properties;
    }

    public void register(RegisterRequest request) {
        String userId = keycloakClient.registerUser(request);
        keycloakClient.assignRealmRole(userId, Role.USER);
    }

    public TokenResponse login(LoginRequest request) {
        return keycloakClient.getToken(
                request.username(),
                request.password()
        );
    }
}