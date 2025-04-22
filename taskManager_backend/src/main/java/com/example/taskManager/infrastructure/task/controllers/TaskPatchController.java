package com.example.taskManager.infrastructure.task.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.taskManager.application.task.dtos.UpdateTaskCompletionOutputDto;
import com.example.taskManager.application.task.dtos.UpdateTaskPriorityInputDto;
import com.example.taskManager.application.task.dtos.UpdateTaskPriorityOutputDto;
import com.example.taskManager.application.task.usecase.TaskCompletionUpdater;
import com.example.taskManager.application.task.usecase.TaskPriorityUpdater;
import com.example.taskManager.infrastructure.auth.dtos.AuthenticatedUserDTO;
import com.example.taskManager.infrastructure.auth.services.AuthenticatedUserProvider;
import com.example.taskManager.infrastructure.task.dtos.PatchTaskCompletionResponseDto;
import com.example.taskManager.infrastructure.task.dtos.PatchTaskPriorityRequestDto;
import com.example.taskManager.infrastructure.task.dtos.PatchTaskPriorityResponseDto;
import com.example.taskManager.infrastructure.task.mappers.PatchTaskCompletionDtoMapper;
import com.example.taskManager.infrastructure.task.mappers.PatchTaskPriorityDtoMapper;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("http://localhost:4200")
public class TaskPatchController {

    private final AuthenticatedUserProvider     authenticatedUserProvider;
    private final PatchTaskCompletionDtoMapper  patchTaskCompletionDtoMapper;
    private final PatchTaskPriorityDtoMapper    patchTaskPriorityDtoMapper;
    private final TaskCompletionUpdater         taskCompletionUpdater;
    private final TaskPriorityUpdater           taskPriorityUpdater;

    public TaskPatchController(
        AuthenticatedUserProvider       authenticatedUserProvider,
        PatchTaskCompletionDtoMapper    patchTaskCompletionDtoMapper,
        PatchTaskPriorityDtoMapper      patchTaskPriorityDtoMapper,
        TaskCompletionUpdater           taskCompletionUpdater,
        TaskPriorityUpdater             taskPriorityUpdater
    ) {
        this.authenticatedUserProvider =    authenticatedUserProvider;
        this.patchTaskCompletionDtoMapper = patchTaskCompletionDtoMapper;
        this.patchTaskPriorityDtoMapper =   patchTaskPriorityDtoMapper;
        this.taskCompletionUpdater =        taskCompletionUpdater;
        this.taskPriorityUpdater =          taskPriorityUpdater;
    }

    @PatchMapping("/{id}/completion")
    public ResponseEntity<PatchTaskCompletionResponseDto> toggleCompletion(@PathVariable Integer id) {
        AuthenticatedUserDTO user = authenticatedUserProvider.execute();

        UpdateTaskCompletionOutputDto output = taskCompletionUpdater.execute(
            patchTaskCompletionDtoMapper.toInput(id, user.getUserId())
        );

        return ResponseEntity.ok(patchTaskCompletionDtoMapper.toResponse(output));
    }

    @PatchMapping("/{id}/priority")
    public ResponseEntity<PatchTaskPriorityResponseDto> updatePriority(@PathVariable Integer id, @RequestBody PatchTaskPriorityRequestDto request) {
        AuthenticatedUserDTO user = authenticatedUserProvider.execute();

        UpdateTaskPriorityInputDto input = patchTaskPriorityDtoMapper.toInput(request);
        input.setId(id);
        input.setUserId(user.getUserId());

        UpdateTaskPriorityOutputDto output = taskPriorityUpdater.execute(input);

        return ResponseEntity.ok(patchTaskPriorityDtoMapper.toResponse(output));
    }

}