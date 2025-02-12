package com.example.taskManager.config; // Usa el nombre correcto de tu paquete

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import java.util.List;

@Configuration
public class CorsConfig {
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200")); // Permitir el frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // Métodos permitidos
        config.setAllowedHeaders(List.of("*")); // Permitir todos los encabezados
        config.setAllowCredentials(true); // Permitir autenticación con cookies o tokens

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactivar CSRF si no usas autenticación basada en sesiones
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Permitir todas las solicitudes
            .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Aplicar la configuración CORS
        
        return http.build();
    }
}
