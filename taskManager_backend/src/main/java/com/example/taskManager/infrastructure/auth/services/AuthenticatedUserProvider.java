package com.example.taskManager.infrastructure.auth.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import com.example.taskManager.domain.user.interfaces.UserRepositoryPort;
import com.example.taskManager.domain.user.models.User;
import com.example.taskManager.infrastructure.auth.dtos.AuthenticatedUserDTO;

@Component
public class AuthenticatedUserProvider {

    private final UserRepositoryPort userRepositoryPort;

    public AuthenticatedUserProvider(
        UserRepositoryPort userRepositoryPort
    ) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public AuthenticatedUserDTO execute() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String auth0Id = jwt.getClaimAsString("sub");

        User user = userRepositoryPort.findByExternalId(auth0Id)
                .orElseThrow();

        return new AuthenticatedUserDTO(auth0Id, user.getId(), user.getRole());
    }

}