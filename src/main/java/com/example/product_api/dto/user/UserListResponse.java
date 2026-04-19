package com.example.product_api.dto.user;

import org.keycloak.representations.idm.UserRepresentation;
import java.util.List;

public record UserListResponse(
        List<UserRepresentation> users,
        List<UserRepresentation> editors
) {}
