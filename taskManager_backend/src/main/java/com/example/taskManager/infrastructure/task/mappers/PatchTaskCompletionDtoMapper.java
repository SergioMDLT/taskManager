package com.example.taskManager.infrastructure.task.mappers;

import org.springframework.stereotype.Component;
import com.example.taskManager.application.task.dtos.UpdateTaskCompletionInputDto;
import com.example.taskManager.application.task.dtos.UpdateTaskCompletionOutputDto;
import com.example.taskManager.infrastructure.task.dtos.PatchTaskCompletionResponseDto;

@Component
public class PatchTaskCompletionDtoMapper {

    public UpdateTaskCompletionInputDto toInput(Integer id, Integer userId) {
        return new UpdateTaskCompletionInputDto(id, userId);
    }

    public PatchTaskCompletionResponseDto toResponse(UpdateTaskCompletionOutputDto output) {
        return new PatchTaskCompletionResponseDto(
            output.getId(),
            output.getTitle(),
            output.getDescription(),
            output.getCompleted(),
            output.getPriority()
        );
    }
    
}