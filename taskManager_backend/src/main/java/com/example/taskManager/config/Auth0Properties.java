package com.example.taskManager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties( prefix = "spring.security.oauth2.resourceserver.jwt" )
public record Auth0Properties( String issuerUri ) {}