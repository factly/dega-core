package com.factly.dega.utils;

import com.factly.dega.service.dto.KeyCloakRoleMappingDTO;
import com.factly.dega.service.dto.KeyCloakRoleMappingDTO1;
import com.factly.dega.service.dto.KeyCloakUserDTO;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ntalla on 4/29/19.
 */
@Component
public class KeycloakUtils {
    private final Logger log = LoggerFactory.getLogger(KeycloakUtils.class);

    private RestTemplate oauth2RestTemplate = null;

    private String keycloakServerURI;

    private String keycloakTokenURI;

    private String keycloakClientId;

    private String keycloakClientSecret;

    @Autowired
    public KeycloakUtils(
        @Value("${keycloak.api.uri}") String keycloakServerURI,
        @Value("${security.oauth2.client.access-token-uri}") String keycloakTokenURI,
        @Value("${security.oauth2.client.client-id}") String clientId,
        @Value("${security.oauth2.client.client-secret}") String clientSecret) {
        this.keycloakServerURI = keycloakServerURI;
        this.keycloakTokenURI = keycloakTokenURI;
        this.keycloakClientId = clientId;
        this.keycloakClientSecret = clientSecret;
    }

    public Boolean create(String usersEndPoint, String jsonAsString) {

        // save the user to keycloak
        try {
            RestTemplate restTemplate = getOauth2RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            log.debug("Keycloak entity request body {}", jsonAsString);
            HttpEntity<String> httpEntity = new HttpEntity(jsonAsString, httpHeaders);
            restTemplate.postForObject(keycloakServerURI + usersEndPoint, httpEntity, String.class);
        } catch (HttpClientErrorException e) {
            if (e.getMessage().equals("403 Forbidden")) {
                log.error("This client {} does not have required access", keycloakClientId);
                return false;
            }
            if (e.getMessage().equals("409 Conflict")) {
                log.error("409-Conflict: An entity already exists");
                return false;
            }
            // for all other errors rethrow
            throw e;
        } catch (Exception e) {
            log.error("keycloak api failed with the message {}, exiting", e.getMessage());
            throw e;
        }
        return true;
    }

    public String getUserId(String endpoint) {
        try {
            RestTemplate restTemplate = getOauth2RestTemplate();
            KeyCloakUserDTO[] keyCloakUsers = restTemplate.getForObject(keycloakServerURI + endpoint, KeyCloakUserDTO[].class);

            if (keyCloakUsers != null && keyCloakUsers.length > 0) {
                return keyCloakUsers[0].getId();
            }
        } catch (HttpClientErrorException e) {
            if (e.getMessage().equals("403 Forbidden")) {
                log.error("This client {} does not have required access", keycloakClientId);
                return "NOT_FOUND";
            }
            // for all other errors rethrow
            throw e;
        } catch (Exception e) {
            log.error("keycloak api failed with the message {}, exiting", e.getMessage());
            throw e;
        }
        return "NOT_FOUND";
    }

    public KeyCloakRoleMappingDTO getRoleMapping(String endpoint) {
        try {
            RestTemplate restTemplate = getOauth2RestTemplate();
            KeyCloakRoleMappingDTO keyCloakMappings =
                restTemplate.getForObject(keycloakServerURI + endpoint, KeyCloakRoleMappingDTO.class);

            if (keyCloakMappings != null) {
                return keyCloakMappings;
            }
        } catch (HttpClientErrorException e) {
            if (e.getMessage().equals("403 Forbidden")) {
                log.error("This client {} does not have required access", keycloakClientId);
                return null;
            }
            // for all other errors rethrow
            throw e;
        } catch (Exception e) {
            log.error("keycloak api failed with the message {}, exiting", e.getMessage());
            throw e;
        }
        return null;
    }

    public KeyCloakRoleMappingDTO getRoleMappingsDTO(String endpoint, String roleName) {
        try {
            RestTemplate restTemplate = getOauth2RestTemplate();
            KeyCloakRoleMappingDTO[] keyCloakRoleMappings =
                restTemplate.getForObject(keycloakServerURI + endpoint, KeyCloakRoleMappingDTO[].class);

            if (keyCloakRoleMappings != null && keyCloakRoleMappings.length > 0) {
                // first delete all the current dega role mappings on the user except current one if exists
                Set<KeyCloakRoleMappingDTO> roleMappings = Arrays.stream(keyCloakRoleMappings)
                    .filter(rm -> !rm.getName().equals(roleName) && rm.getDescription().startsWith("DEGA_ROLE"))
                    .collect(Collectors.toSet());
                if (roleMappings != null && roleMappings.size() > 0) {
                    JsonObject jObj = (JsonObject)new GsonBuilder().create().toJsonTree(roleMappings);
                    String roleMappingAsString = jObj.toString();
                    restTemplate.delete(keycloakServerURI + endpoint, roleMappingAsString);
                }

                return Arrays.stream(keyCloakRoleMappings).filter(rm -> rm.getName().equals(roleName)).findFirst().get();
            }
        } catch (HttpClientErrorException e) {
            if (e.getMessage().equals("403 Forbidden")) {
                log.error("This client {} does not have required access", keycloakClientId);
                return null;
            }
            // for all other errors rethrow
            throw e;
        } catch (Exception e) {
            log.error("keycloak api failed with the message {}, exiting", e.getMessage());
            throw e;
        }
        return null;
    }

    private RestTemplate getOauth2RestTemplate() {
        if (oauth2RestTemplate == null) {
            ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
            resourceDetails.setClientId(keycloakClientId);
            resourceDetails.setClientSecret(keycloakClientSecret);
            resourceDetails.setAccessTokenUri(keycloakTokenURI);
            oauth2RestTemplate = new OAuth2RestTemplate(resourceDetails);
        }
        return oauth2RestTemplate;
    }
}
