package com.factly.dega.config;

import com.factly.dega.aop.clientdetails.ClientDetailsAspect;
import com.factly.dega.service.DegaUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class ClientDetailsAspectConfiguration {

    private final Environment env;

    private final DegaUserService degaUserService;

    public ClientDetailsAspectConfiguration(Environment env, DegaUserService degaUserService) {
        this.env = env;
        this.degaUserService = degaUserService;
    }

    @Bean
    public ClientDetailsAspect clientDetailsAspect(Environment env, DegaUserService degaUserService) {
        return new ClientDetailsAspect(env, degaUserService);
    }
}
