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
            if (context != null && context.getAttribute(Constants.CLIENT_ID) == null) {
                OAuth2Authentication auth = (OAuth2Authentication) context.getUserPrincipal();
                if(auth != null) {
                    String principal = (String) auth.getPrincipal();
                    context.setAttribute("UserID", principal);

                    if (principal.startsWith("service-account-")) {
                        // Request with API token
                        String[] tokens = principal.split("service-account-");

                        if (tokens.length == 2) {
                            String clientID = tokens[1];
                            context.setAttribute(Constants.CLIENT_ID, clientID);
                        } else {
                            log.warn("No client found with the principal {}, exiting", principal);
                        }
                    } else {
                        // request with user login
                        String userId = principal;
                        Optional<DegaUserDTO> user = degaUserService.findByEmailId(userId);
                        log.info("Login userID is {}", userId);
                        if (user.isPresent()) {
                            // get the default org dto
                            OrganizationDTO orgDTO = user.get()
                                .getOrganizations()
                                .stream()
                                .filter(o -> o.getId().equals(user.get().getOrganizationDefaultId()))
                                .findAny()
                                .orElse(null);
                            if (orgDTO != null) {
                                String clientId = orgDTO.getClientId();
                                context.setAttribute(Constants.CLIENT_ID, clientId);
                            } else {
                                log.warn("No org found with the default org id {}, exiting", user.get().getOrganizationDefaultId());
                            }
                        }
                    }
                }
            }

        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

            throw e;
        }
    }
}
