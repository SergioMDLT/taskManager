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
        if ( auth0Id == null || email == null || email.isBlank() ) {
            throw new IllegalArgumentException( "auth0Id and email can not be null or empty" );
        }

        return userRepository.findByAuth0Id( auth0Id )
            .orElseGet(() -> createUser( auth0Id, email ));
    }

    private User createUser(String auth0Id, String email) {
        System.out.println("⚡ Intentando crear usuario con email: " + email);
    
        User newUser = new User();
        newUser.setAuth0Id(auth0Id);
        newUser.setEmail(email);
        newUser.setRole("user");
    
        try {
            User savedUser = userRepository.save(newUser);
            System.out.println("✅ Usuario guardado en la BD con ID: " + savedUser.getId());
            return savedUser;
        } catch (Exception e) {
            System.err.println("❌ ERROR al guardar usuario en BD: " + e.getMessage());
            e.printStackTrace(); // 🔥 Mostrará el error exacto en la consola
            throw new RuntimeException("Error al guardar usuario en la BD: " + e.getMessage());
        }
    }     

}
