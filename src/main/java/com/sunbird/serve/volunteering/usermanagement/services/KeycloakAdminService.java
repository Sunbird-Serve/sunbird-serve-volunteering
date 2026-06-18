package com.sunbird.serve.volunteering.usermanagement.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

/**
 * Service for interacting with the Keycloak Admin REST API.
 * Uses a service account (client credentials grant) to assign realm roles to users.
 */
@Service
@Slf4j
public class KeycloakAdminService {

    private final WebClient webClient;

    @Value("${keycloak.admin.url}")
    private String keycloakUrl;

    @Value("${keycloak.admin.realm}")
    private String realm;

    @Value("${keycloak.admin.client-id}")
    private String clientId;

    @Value("${keycloak.admin.client-secret}")
    private String clientSecret;

    public KeycloakAdminService() {
        this.webClient = WebClient.builder().build();
    }

    /**
     * Assigns a realm role to a user in Keycloak.
     * Failures are logged but never thrown — user creation should not fail due to role assignment issues.
     */
    public void assignRealmRole(String keycloakUserId, String roleName) {
        try {
            log.info("Assigning Keycloak realm role '{}' to user '{}'", roleName, keycloakUserId);

            // Step 1: Get service account token
            String accessToken = getServiceAccountToken();
            if (accessToken == null) {
                log.error("Failed to get service account token, skipping role assignment for user '{}'", keycloakUserId);
                return;
            }

            // Step 2: Get role representation (to get role ID)
            Map<String, Object> roleRepresentation = getRoleRepresentation(accessToken, roleName);
            if (roleRepresentation == null) {
                log.warn("Role '{}' not found in Keycloak realm '{}', skipping assignment", roleName, realm);
                return;
            }

            // Step 3: Assign role to user
            String roleId = (String) roleRepresentation.get("id");
            assignRoleToUser(accessToken, keycloakUserId, roleId, roleName);

            log.info("Successfully assigned role '{}' to user '{}'", roleName, keycloakUserId);
        } catch (Exception e) {
            log.warn("Failed to assign Keycloak role '{}' to user '{}': {}", roleName, keycloakUserId, e.getMessage());
        }
    }

    /**
     * Gets a service account token using client credentials grant.
     */
    private String getServiceAccountToken() {
        try {
            String tokenUrl = keycloakUrl + "/realms/" + realm + "/protocol/openid-connect/token";

            @SuppressWarnings("unchecked")
            Map<String, Object> tokenResponse = webClient.post()
                    .uri(tokenUrl)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData("grant_type", "client_credentials")
                            .with("client_id", clientId)
                            .with("client_secret", clientSecret))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (tokenResponse != null && tokenResponse.containsKey("access_token")) {
                return (String) tokenResponse.get("access_token");
            }

            log.error("Token response did not contain access_token");
            return null;
        } catch (WebClientResponseException e) {
            log.error("Failed to get service account token: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            log.error("Failed to get service account token: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Gets the role representation (including ID) from Keycloak.
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> getRoleRepresentation(String accessToken, String roleName) {
        try {
            String roleUrl = keycloakUrl + "/admin/realms/" + realm + "/roles/" + roleName;

            return webClient.get()
                    .uri(roleUrl)
                    .headers(h -> h.setBearerAuth(accessToken))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            log.warn("Role '{}' does not exist in Keycloak realm '{}'", roleName, realm);
            return null;
        } catch (Exception e) {
            log.error("Failed to get role representation for '{}': {}", roleName, e.getMessage());
            return null;
        }
    }

    /**
     * Assigns a role to a user via the Keycloak Admin API.
     */
    private void assignRoleToUser(String accessToken, String userId, String roleId, String roleName) {
        String assignUrl = keycloakUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";

        List<Map<String, String>> rolePayload = List.of(Map.of(
                "id", roleId,
                "name", roleName
        ));

        webClient.post()
                .uri(assignUrl)
                .headers(h -> h.setBearerAuth(accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(rolePayload)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
