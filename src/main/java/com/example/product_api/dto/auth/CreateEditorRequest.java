package com.example.product_api.dto.auth;

public record CreateEditorRequest(
        String username,
        String email,
        String firstName,
        String lastName
) {}