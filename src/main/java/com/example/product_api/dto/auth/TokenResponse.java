package com.example.product_api.dto.auth;

public record TokenResponse(
        String access_token,
        String refresh_token,
        int expires_in
) {}
