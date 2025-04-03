package com.example.taskManager.application.auth.usecase;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.example.taskManager.domain.user.interfaces.UserRepositoryPort;
import com.example.taskManager.infrastructure.user.entities.User;

@Service
public class UserValidatorOrCreator {

    private final UserRepositoryPort userRepository;

    public UserValidatorOrCreator(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(OAuth2User auth0User) {
        String auth0Id = auth0User.getAttribute("sub");
        String email = auth0User.getAttribute("email");

        return userRepository.findByAuth0Id(auth0Id)
            .orElseGet(() -> {
                User user = new User();
                user.setAuth0Id(auth0Id);
                user.setEmail(email);
                user.setRole("user");
                return userRepository.save(user);
            });
    }
}
