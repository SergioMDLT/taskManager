package com.example.taskManager.auth.application;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.taskManager.auth.domain.User;
import com.example.taskManager.auth.domain.UserRepository;

@Service
public class AuthService {
    
    private final UserRepository userRepository;

    public AuthService( UserRepository userRepository ) {
        this.userRepository = userRepository;
    }

    public User validateOrCreateUser( OAuth2User auth0User ) {
        String auth0Id = auth0User.getAttribute( "sub" );
        String email = auth0User.getAttribute( "email" );

        return userRepository.findByAuth0Id( auth0Id )
        .orElseGet(() -> {
            User newUser = User.builder()
                    .auth0Id( auth0Id )
                    .email( email )
                    .build();
            return userRepository.save( newUser );
        });        
    }

}
