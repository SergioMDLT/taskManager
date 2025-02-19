package com.example.taskManager.user.infrastructure;

import com.example.taskManager.auth.application.AuthService;
import com.example.taskManager.auth.application.dto.AuthenticatedUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/users" )
public class UserController {

    private final AuthService authService;

    public UserController( AuthService authService ) {
        this.authService = authService;
    }

    @GetMapping( "/me" )
    public ResponseEntity<AuthenticatedUserDTO> getAuthenticatedUser() {
        return ResponseEntity.ok( authService.getAuthenticatedUser() );
    }
}
