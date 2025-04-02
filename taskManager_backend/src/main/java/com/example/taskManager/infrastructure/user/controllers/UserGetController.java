package com.example.taskManager.infrastructure.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.taskManager.application.user.auth.usecase.AuthenticatedUserProvider;
import com.example.taskManager.infrastructure.auth.dtos.AuthenticatedUserDTO;

@RestController
@RequestMapping("/users")
public class UserGetController {

    private final AuthenticatedUserProvider authenticatedUserProvider;

    public UserGetController(AuthenticatedUserProvider authenticatedUserProvider) {
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @GetMapping("/me")
    public ResponseEntity<AuthenticatedUserDTO> getAuthenticatedUser() {
        return ResponseEntity.ok(authenticatedUserProvider.execute());
    }

}