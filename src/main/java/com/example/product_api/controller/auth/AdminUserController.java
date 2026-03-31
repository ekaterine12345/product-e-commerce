package com.example.product_api.controller.auth;

import com.example.product_api.dto.auth.CreateEditorRequest;
import com.example.product_api.service.user.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @PostMapping("/editors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createEditor(@RequestBody CreateEditorRequest request) {
        adminUserService.createEditor(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}