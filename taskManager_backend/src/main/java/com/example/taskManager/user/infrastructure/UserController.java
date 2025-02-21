package com.example.taskManager.user.infrastructure;

import com.example.taskManager.auth.application.AuthService;
import com.example.taskManager.auth.application.dto.AuthenticatedUserDTO;
import com.example.taskManager.user.application.UserService;
import com.example.taskManager.user.application.dto.UserRequestDTO;
import com.example.taskManager.user.domain.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/users" )
public class UserController {

    private final AuthService authService;
    private final UserService userService;

    public UserController( AuthService authService, UserService userService ) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping( "/me" )
    public ResponseEntity<AuthenticatedUserDTO> getAuthenticatedUser() {
        return ResponseEntity.ok( authService.getAuthenticatedUser() );
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDTO userDTO) {
        try {
            System.out.println("📩 Recibida petición de registro con auth0Id: " + userDTO.getAuth0Id());

            User user = userService.findOrCreateUser(userDTO.getAuth0Id(), userDTO.getEmail());

            System.out.println("✅ Usuario guardado en la BD: " + user.getEmail());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            System.err.println("❌ ERROR en registerUser: " + e.getMessage());
            e.printStackTrace(); // 🔥 Mostrará el error en la consola
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }


}
