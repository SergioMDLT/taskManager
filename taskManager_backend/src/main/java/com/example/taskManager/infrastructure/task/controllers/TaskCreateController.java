package com.example.taskManager.infrastructure.task.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.taskManager.application.task.usecase.TaskCreator;
import com.example.taskManager.application.user.auth.usecase.AuthService;
import com.example.taskManager.infrastructure.task.dtos.TaskRequestDTO;
import com.example.taskManager.infrastructure.task.dtos.TaskResponseDTO;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("http://localhost:4200")
public class TaskCreateController {

    private final AuthService authService;
    private final TaskCreator taskCreator;

    public TaskCreateController(TaskCreator taskCreator, AuthService authService) {
        this.authService = authService;
        this.taskCreator = taskCreator;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        String auth0Id = authService.getAuthenticatedUser().getAuth0Id();
        taskRequestDTO.setAuth0Id(auth0Id);
        return ResponseEntity.ok(taskCreator.execute(taskRequestDTO));
    }

}