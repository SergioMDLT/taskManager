package com.example.taskManager.infrastructure.task.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.taskManager.application.task.dtos.GetTaskOutputDto;
import com.example.taskManager.application.task.dtos.TaskSearchFiltersDTO;
import com.example.taskManager.application.task.usecase.TaskGetterById;
import com.example.taskManager.application.task.usecase.TaskSearcher;
import com.example.taskManager.infrastructure.auth.services.AuthenticatedUserProvider;
import com.example.taskManager.infrastructure.task.dtos.GetTaskResponseDto;
import com.example.taskManager.infrastructure.task.dtos.SearchTaskResponseDto;
import com.example.taskManager.infrastructure.task.mappers.GetTaskDtoMapper;
import com.example.taskManager.infrastructure.task.mappers.SearchTaskDtoMapper;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("http://localhost:4200")
public class TaskGetController {

    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final TaskGetterById            taskGetterById;
    private final GetTaskDtoMapper          getTaskDtoMapper;
    private final SearchTaskDtoMapper       searchTaskDtoMapper;
    private final TaskSearcher              taskSearcher;

    public TaskGetController(
            AuthenticatedUserProvider   authenticatedUserProvider,
            TaskGetterById              taskGetterById,
            GetTaskDtoMapper            getTaskDtoMapper,
            SearchTaskDtoMapper         searchTaskDtoMapper,
            TaskSearcher                taskSearcher
        ) {
            this.authenticatedUserProvider =    authenticatedUserProvider;
            this.taskGetterById =               taskGetterById;
            this.getTaskDtoMapper =             getTaskDtoMapper;
            this.searchTaskDtoMapper =          searchTaskDtoMapper;
            this.taskSearcher =                 taskSearcher;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTaskResponseDto> getTaskById(@PathVariable Integer id) {
        GetTaskOutputDto output = taskGetterById.execute(id);
        return ResponseEntity.ok(getTaskDtoMapper.toResponse(output));
    }

    @GetMapping
    public ResponseEntity<Page<SearchTaskResponseDto>> getTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Boolean completed,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {

        Integer userId = authenticatedUserProvider.execute().getUserId();
        TaskSearchFiltersDTO filters = new TaskSearchFiltersDTO(userId, title, completed, page, size, sort);
        
        Page<SearchTaskResponseDto> response = taskSearcher.search(filters)
            .map(searchTaskDtoMapper::toResponse);
        
        return ResponseEntity.ok(response);
    }

}