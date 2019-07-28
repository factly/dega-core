package com.factly.dega.utils;

import com.factly.dega.service.dto.KeyCloakUserDTO;
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

        // save the user to keycloak
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
