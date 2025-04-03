package com.example.taskManager.infrastructure.task.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.taskManager.application.auth.usecase.AuthenticatedUserProvider;
import com.example.taskManager.application.task.usecase.TaskGetterById;
import com.example.taskManager.application.task.usecase.TaskSearcher;
import com.example.taskManager.infrastructure.task.dtos.TaskResponseDTO;
import com.example.taskManager.infrastructure.task.dtos.TaskSearchFiltersDTO;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("http://localhost:4200")
public class TaskGetController {

    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final TaskGetterById            taskGetterById;
    private final TaskSearcher              taskSearcher;

    public TaskGetController(
            AuthenticatedUserProvider   authenticatedUserProvider,
            TaskGetterById              taskGetterById,
            TaskSearcher                taskSearcher
        ) {
            this.authenticatedUserProvider =    authenticatedUserProvider;
            this.taskGetterById =               taskGetterById;
            this.taskSearcher =                 taskSearcher;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Integer id) {
        return ResponseEntity.ok(taskGetterById.execute(id));
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> getTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {

        String auth0Id = authenticatedUserProvider.execute().getAuth0Id();

        TaskSearchFiltersDTO filters = new TaskSearchFiltersDTO(auth0Id, title, completed, page, size, sort);
        return ResponseEntity.ok(taskSearcher.search(filters));
    }

}