package com.example.taskManager.infrastructure.task.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.taskManager.application.task.usecase.TaskUpdater;
import com.example.taskManager.infrastructure.task.dtos.TaskResponseDTO;
import com.example.taskManager.infrastructure.task.dtos.TaskUpdateRequestDTO;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("http://localhost:4200")
public class TaskUpdateController {

    private final TaskUpdater taskUpdater;

    public TaskUpdateController(TaskUpdater taskUpdater) {
        this.taskUpdater = taskUpdater;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Integer id,
                                                      @RequestBody TaskUpdateRequestDTO request) {
        return ResponseEntity.ok(taskUpdater.execute(id, request));
    }

}