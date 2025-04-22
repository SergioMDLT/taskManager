package com.example.taskManager.infrastructure.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import com.example.taskManager.infrastructure.auth.config.Auth0Properties;

@Configuration
public class Auth0JwtDecoder {

    private final Auth0Properties auth0Properties;

    public Auth0JwtDecoder(Auth0Properties auth0Properties) {
        this.auth0Properties = auth0Properties;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withJwkSetUri(auth0Properties.issuerUri().replaceAll("/$", "") + "/.well-known/jwks.json").build();
    }

}