package com.example.taskManager.auth.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {
        http
            .cors( cors -> cors.configurationSource( corsConfigurationSource() ))
            .csrf( csrf -> csrf.disable() )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            );

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins( List.of( "http://localhost:4200" )); // Permite Angular
        config.setAllowedMethods( List.of( "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS" ));
        config.setAllowedHeaders( List.of( "Authorization", "Content-Type" ));
        config.setAllowCredentials( true );
        source.registerCorsConfiguration( "/**", config );
        return new CorsFilter( source );
    }

    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins( List.of( "http://localhost:4200" ));
        config.setAllowedMethods( List.of( "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS" ));
        config.setAllowedHeaders( List.of( "Authorization", "Content-Type" ));
        config.setAllowCredentials( true );
        source.registerCorsConfiguration( "/**", config );
        return source;
    }
}
