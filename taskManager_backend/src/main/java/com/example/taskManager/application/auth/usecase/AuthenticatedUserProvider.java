package com.example.taskManager.application.auth.usecase;

import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import com.example.taskManager.domain.user.interfaces.UserRepositoryPort;
import com.example.taskManager.infrastructure.auth.dtos.AuthenticatedUserDTO;
import com.example.taskManager.infrastructure.user.entities.User;

@Service
public class AuthenticatedUserProvider {

    private final UserRepositoryPort userRepository;

    public AuthenticatedUserProvider(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    public AuthenticatedUserDTO execute() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Jwt jwt) {
            String auth0Id = jwt.getClaimAsString("sub");

            Integer userId = userRepository.findByAuth0Id(auth0Id)
                .map(User::getId)
                .orElseThrow(() -> new IllegalStateException("User not found in database"));

            List<String> roles = jwt.getClaimAsStringList("https://your-app.com/roles");
            String role = (roles != null && !roles.isEmpty()) ? roles.get(0) : "user";

            return new AuthenticatedUserDTO(auth0Id, userId, role);
        }

        throw new IllegalStateException("No authenticated user found");
    }
}
