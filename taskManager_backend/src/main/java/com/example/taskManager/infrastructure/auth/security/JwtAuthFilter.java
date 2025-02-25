package com.example.taskManager.infrastructure.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    public JwtAuthFilter( JwtDecoder jwtDecoder ) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain ) throws ServletException, IOException {
        String authHeader = request.getHeader( "Authorization" );

        if ( authHeader != null && authHeader.startsWith( "Bearer " )) {
            String token = authHeader.substring( 7 );

            try {
                Jwt jwt = jwtDecoder.decode( token );

                List<SimpleGrantedAuthority> authorities = jwt.getClaimAsStringList( "permissions" ).stream()
                        .map( SimpleGrantedAuthority::new )
                        .collect( Collectors.toList() );

                JwtAuthenticationToken authToken = new JwtAuthenticationToken( jwt, authorities );
                SecurityContextHolder.getContext().setAuthentication( authToken );
            } catch ( Exception e ) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter( request, response );
    }
}
