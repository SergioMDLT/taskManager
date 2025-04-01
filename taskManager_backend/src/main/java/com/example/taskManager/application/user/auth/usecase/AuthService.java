package com.example.taskManager.application.user.auth.usecase;

import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import com.example.taskManager.infrastructure.auth.dtos.AuthenticatedUserDTO;
import com.example.taskManager.infrastructure.user.entities.User;
import com.example.taskManager.infrastructure.user.repositories.UserRepository;

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

    public AuthenticatedUserDTO getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
        if ( principal instanceof Jwt jwt ) {
            String auth0Id = jwt.getClaimAsString( "sub" );
    
            Integer userId = userRepository.findByAuth0Id( auth0Id )
                .map( User::getId )
                .orElseThrow(() -> new IllegalStateException( "User not found in database" ));
    
            List<String> roles = jwt.getClaimAsStringList( "https://your-app.com/roles" );
            // TO DO: configurar la petición de los roles a auth0
            
            String role = roles != null && !roles.isEmpty() ? roles.get( 0 ) : "user";
    
            return new AuthenticatedUserDTO( auth0Id, userId, role );
        }
    
        throw new IllegalStateException("No authenticated user found");
    }

}