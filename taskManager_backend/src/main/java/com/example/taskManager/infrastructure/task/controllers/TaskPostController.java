package com.example.taskManager.infrastructure.task.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.taskManager.application.task.dtos.CreateTaskInputDto;
import com.example.taskManager.application.task.dtos.CreateTaskOutputDto;
import com.example.taskManager.application.task.usecase.TaskCreator;
import com.example.taskManager.infrastructure.auth.services.AuthenticatedUserProvider;
import com.example.taskManager.infrastructure.task.dtos.CreateTaskRequestDto;
import com.example.taskManager.infrastructure.task.dtos.CreateTaskResponseDto;
import com.example.taskManager.infrastructure.task.mappers.PostTaskDtoMapper;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("http://localhost:4200")
public class TaskPostController {

    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final TaskCreator               taskCreator;
    private final PostTaskDtoMapper         postTaskDtoMapper;

    public TaskPostController(
        AuthenticatedUserProvider   authenticatedUserProvider,
        TaskCreator                 taskCreator,
        PostTaskDtoMapper           postTaskDtoMapper
    ) {
        this.authenticatedUserProvider =    authenticatedUserProvider;
        this.taskCreator =                  taskCreator;
        this.postTaskDtoMapper =            postTaskDtoMapper;
    }

    @PostMapping
    public ResponseEntity<CreateTaskResponseDto> createTask(@RequestBody CreateTaskRequestDto createTaskRequestDTO) {
        Integer userId = authenticatedUserProvider.execute().getUserId();

        CreateTaskInputDto input = postTaskDtoMapper.toInput(createTaskRequestDTO, userId);
        CreateTaskOutputDto output = taskCreator.execute(input);

        return ResponseEntity.ok(postTaskDtoMapper.toResponse(output));
    }

}