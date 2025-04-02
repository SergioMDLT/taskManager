package com.example.taskManager.infrastructure.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.taskManager.application.user.usecase.UserCreator;
import com.example.taskManager.infrastructure.user.dtos.UserRequestDTO;
import com.example.taskManager.infrastructure.user.entities.User;

@RestController
@RequestMapping("/users")
public class UserCreateController {

    private final UserCreator userCreator;

    public UserCreateController(UserCreator userCreator) {
        this.userCreator = userCreator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDTO userDTO) {
        try {
            User user = userCreator.findOrCreateUser(userDTO.getAuth0Id(), userDTO.getEmail());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }

}