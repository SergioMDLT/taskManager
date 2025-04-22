package com.example.taskManager.infrastructure.task.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.taskManager.application.task.dtos.UpdateTaskInputDto;
import com.example.taskManager.application.task.dtos.UpdateTaskOutputDto;
import com.example.taskManager.application.task.usecase.TaskUpdater;
import com.example.taskManager.infrastructure.auth.dtos.AuthenticatedUserDTO;
import com.example.taskManager.infrastructure.auth.services.AuthenticatedUserProvider;
import com.example.taskManager.infrastructure.task.dtos.PatchTaskRequestDto;
import com.example.taskManager.infrastructure.task.dtos.PatchTaskResponseDto;
import com.example.taskManager.infrastructure.task.mappers.PatchTaskDtoMapper;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("http://localhost:4200")
public class TaskPatchController {

    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final PatchTaskDtoMapper        patchTaskDtoMapper;
    private final TaskUpdater               taskUpdater;

    public TaskPatchController(
        AuthenticatedUserProvider   authenticatedUserProvider,
        PatchTaskDtoMapper          patchTaskDtoMapper,
        TaskUpdater                 taskUpdater
    ) {
        this.patchTaskDtoMapper =           patchTaskDtoMapper;
        this.taskUpdater =                  taskUpdater;
        this.authenticatedUserProvider =    authenticatedUserProvider;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatchTaskResponseDto> updateTask(@PathVariable Integer id, @RequestBody PatchTaskRequestDto request) {
        AuthenticatedUserDTO user = authenticatedUserProvider.execute();
        UpdateTaskInputDto input = patchTaskDtoMapper.toInput(request, user.getUserId());
        input.setId(id);
        UpdateTaskOutputDto output = taskUpdater.execute(input);

        return ResponseEntity.ok(patchTaskDtoMapper.toResponse(output));
    }

}