package com.example.taskManager.infrastructure.user.controllers;

import com.example.taskManager.application.user.auth.usecase.AuthService;
import com.example.taskManager.application.user.usecase.UserCreator;
import com.example.taskManager.infrastructure.auth.dtos.AuthenticatedUserDTO;
import com.example.taskManager.infrastructure.user.dtos.UserRequestDTO;
import com.example.taskManager.infrastructure.user.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final AuthService authService;
    private final UserCreator userCreator;

    public UserController(AuthService authService, UserCreator userCreator) {
        this.authService = authService;
        this.userCreator = userCreator;
    }

    @GetMapping("/me")
    public ResponseEntity<AuthenticatedUserDTO> getAuthenticatedUser() {
        return ResponseEntity.ok(authService.getAuthenticatedUser());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDTO userDTO) {
        try {
            System.out.println("📩 Recibida petición de registro con auth0Id: " + userDTO.getAuth0Id());

            User user = userCreator.findOrCreateUser(userDTO.getAuth0Id(), userDTO.getEmail());

            System.out.println("✅ Usuario guardado en la BD: " + user.getEmail());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            System.err.println("❌ ERROR en registerUser: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }

}
