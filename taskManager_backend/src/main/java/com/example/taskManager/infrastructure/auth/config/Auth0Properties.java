package com.example.taskManager.infrastructure.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties( prefix = "spring.security.oauth2.resourceserver.jwt" )
public record Auth0Properties( String issuerUri ) {}