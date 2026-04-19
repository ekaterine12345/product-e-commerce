package com.example.product_api.service.auth;

import com.example.product_api.config.KeycloakProperties;
import com.example.product_api.dto.auth.RegisterRequest;
import com.example.product_api.dto.auth.TokenResponse;
import com.example.product_api.exception.user.KeycloakUserCreationException;
import com.example.product_api.exception.user.UserAlreadyExistsException;
import com.example.product_api.enums.Role;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class KeycloakClient {

    private final Keycloak keycloak;
    private final KeycloakProperties properties;
    private final RestTemplate restTemplate = new RestTemplate();

    public KeycloakClient(Keycloak keycloak, KeycloakProperties properties) {
        this.keycloak = keycloak;
        this.properties = properties;
    }


    public List<UserRepresentation> getUsersByRole(Role role) {
        return realm()
                .roles()
                .get(role.name())
                .getUserMembers();
    }

    private void setPassword(String userId, String password) {

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setTemporary(false);
        credential.setValue(password);

        keycloak.realm(properties.getRealm())
                .users()
                .get(userId)
                .resetPassword(credential);
    }

    public String createUser(UserRepresentation user) {

        user.setAttributes(Map.of(
                "createdBy", List.of("product-client")
        ));

        try (Response response = realm().users().create(user)) {

            int status = response.getStatus();

            if (status == 409) {
                throw new UserAlreadyExistsException(user.getUsername(), user.getEmail());
            }
            if (status == 400) {
                throw new KeycloakUserCreationException("Invalid user data");
            }
            if (status != 201) {
                throw new KeycloakUserCreationException("Failed: " + status);
            }

            return extractUserId(response);
        }
    }


    private String extractUserId(Response response) {
        String location = response.getHeaderString("Location");
        return location.substring(location.lastIndexOf("/") + 1);
    }

    public void sendPasswordResetEmail(String userId) {
        keycloak.realm(properties.getRealm())
                .users()
                .get(userId)
                .executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    public String registerUser(RegisterRequest request) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEnabled(true);
        user.setEmailVerified(true);

        String userId = createUser(user);
        setPassword(userId, request.password());

        return userId;
    }

    private RealmResource realm() {
        return keycloak.realm(properties.getRealm());
    }

    public void assignRealmRole(String userId, Role roleName) {

        RoleResource roleResource = keycloak
                .realm(properties.getRealm())
                .roles()
                .get(roleName.name());

        RoleRepresentation role = roleResource.toRepresentation();

        keycloak.realm(properties.getRealm())
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(List.of(role));
    }

    public TokenResponse getToken(String username, String password) {

        String url = properties.getServerUrl()
                + "/realms/"
                + properties.getRealm()
                + "/protocol/openid-connect/token";

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("client_id", properties.getClientId());
        body.add("client_secret", properties.getClientSecret());
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);

        return restTemplate.postForObject(url, body, TokenResponse.class);
    }
}
