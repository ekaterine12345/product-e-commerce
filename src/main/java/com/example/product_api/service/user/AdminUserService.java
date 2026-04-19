package com.example.product_api.service.user;

import com.example.product_api.dto.auth.CreateEditorRequest;
import com.example.product_api.dto.user.UserListResponse;
import com.example.product_api.enums.Role;
import com.example.product_api.service.auth.KeycloakClient;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final KeycloakClient keycloakClient;

    public void createEditor(CreateEditorRequest request) {
        String userId = keycloakClient.createUser(buildUser(request));
        keycloakClient.assignRealmRole(userId, Role.EDITOR);
        keycloakClient.sendPasswordResetEmail(userId);
    }

    private UserRepresentation buildUser(CreateEditorRequest request) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEnabled(true);
        user.setEmailVerified(false);
        return user;
    }

    public UserListResponse getAllUsersAndEditors() {
        List<UserRepresentation> users = keycloakClient.getUsersByRole(Role.USER);
        List<UserRepresentation> editors = keycloakClient.getUsersByRole(Role.EDITOR);

        return new UserListResponse(users, editors);
    }
}
