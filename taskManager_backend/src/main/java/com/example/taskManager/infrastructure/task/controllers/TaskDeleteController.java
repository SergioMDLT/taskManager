package com.example.taskManager.infrastructure.task.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.taskManager.application.task.usecase.TaskDeleter;
import com.example.taskManager.application.user.auth.usecase.AuthService;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("http://localhost:4200")
public class TaskDeleteController {

    private final AuthService authService;
    private final TaskDeleter taskDeleter;

    public TaskDeleteController(AuthService authService, TaskDeleter taskDeleter) {
        this.authService = authService;
        this.taskDeleter = taskDeleter;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        String auth0Id = authService.getAuthenticatedUser().getAuth0Id();
        taskDeleter.execute(id, auth0Id);
        return ResponseEntity.noContent().build();
    }
    
}