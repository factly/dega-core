package com.factly.dega.aop.clientdetails;

import com.factly.dega.config.Constants;
import com.factly.dega.service.DegaUserService;
import com.factly.dega.service.dto.DegaUserDTO;
import com.factly.dega.service.dto.OrganizationDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

/**
 * Aspect to retrieve client id from token/user_login on all Rest Controller Spring components.
 *
 * By default, it only runs with the "dev" profile.
 */
@Aspect
public class ClientDetailsAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final Environment env;

    @Autowired
    private HttpServletRequest context;

    @Autowired
    private DegaUserService degaUserService;

    public ClientDetailsAspect(Environment env) {
        this.env = env;
    }

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut("within(com.factly.dega.web.rest..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that retrieves client id when a rest controller method is entered.
     *
     * @param joinPoint join point for advice
     * @throws Throwable throws IllegalArgumentException
     */
    @Before("applicationPackagePointcut() && springBeanPointcut()")
    public void retrieveClientID(JoinPoint joinPoint) throws Throwable {
        try {
            if (context == null) {
                log.warn("Context undefined, exiting");
                return;
            }

            Object clientId = context.getAttribute(Constants.CLIENT_ID);
            if (clientId != null) {
                String cId = (String) clientId;
                log.info("Client ID is already set to {} in the context attribute, returning", cId);
                return;
            }

            // get user principal
            OAuth2Authentication auth = (OAuth2Authentication) context.getUserPrincipal();
            if(auth == null) {
                log.info("User principal is undefined (probably because of the public media api), exiting");
                return;
            }

            String principal = (String) auth.getPrincipal();
            if (principal.startsWith("service-account-")) {
                log.info("Service account login detected");
                // Request with API token
                String[] tokens = principal.split("service-account-");
                if (tokens.length != 2) {
                    log.warn("No client found with the principal {}, exiting", principal);
                    return;
                }

                clientId = tokens[1];
                log.info("Setting client id to {} and user id to {}", clientId, principal);
                context.setAttribute(Constants.CLIENT_ID, clientId);
                context.setAttribute(Constants.USER_ID, principal);
                return;
            }

            //if we are here, it means the login is user based
            // request with user login
            log.info("User account login detected");
            String userId = principal;
            Optional<DegaUserDTO> user = degaUserService.findByEmailId(userId);
            if (!user.isPresent()) {
                log.warn("No dega user found with the id {}, exiting", userId);
                return;
            }

            String orgId = (user.get().getOrganizationCurrentId() != null) ?
                user.get().getOrganizationCurrentId() : user.get().getOrganizationDefaultId();
            if (orgId == null) {
                log.warn("User with id {} is not associated with any organization, exiting", orgId);
                return;
            }

            // get the organization
            OrganizationDTO orgDTO = user.get()
                .getOrganizations()
                .stream()
                .filter(o -> o.getId().equals(orgId))
                .findAny()
                .orElse(null);
            if (orgDTO == null) {
                log.warn("No organization found with the org id {}, exiting", user.get().getOrganizationDefaultId());
                return;
            }

            clientId = orgDTO.getClientId();
            log.info("Setting client id to {} and user id to {}", clientId, principal);
            context.setAttribute(Constants.CLIENT_ID, clientId);
            context.setAttribute(Constants.USER_ID, principal);
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }
}
