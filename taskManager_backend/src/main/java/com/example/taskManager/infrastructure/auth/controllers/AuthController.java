package com.example.taskManager.infrastructure.auth.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/auth" )
public class AuthController {

    @GetMapping( "/me" )
    public ResponseEntity<String> getUserInfo( @AuthenticationPrincipal OAuth2User auth0User ) {
        String email = auth0User.getAttribute( "email" );
        return ResponseEntity.ok( "Bienvenido, " + email );
    }
    
}
