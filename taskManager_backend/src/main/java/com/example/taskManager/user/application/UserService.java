package com.example.taskManager.user.application;

import org.springframework.stereotype.Service;
import com.example.taskManager.user.domain.User;
import com.example.taskManager.user.domain.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService( UserRepository userRepository ) {
        this.userRepository = userRepository;
    }

    public User findOrCreateUser( String auth0Id, String email ) {
        return userRepository.findByAuth0Id( auth0Id )
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setAuth0Id( auth0Id );
                newUser.setEmail( email );
                newUser.setRole( "user" );
                return userRepository.save( newUser );
            });
    }

}
