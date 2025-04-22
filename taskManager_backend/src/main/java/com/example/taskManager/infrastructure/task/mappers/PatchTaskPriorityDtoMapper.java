package com.example.taskManager.infrastructure.task.mappers;

import org.springframework.stereotype.Component;
import com.example.taskManager.application.task.dtos.UpdateTaskPriorityInputDto;
import com.example.taskManager.application.task.dtos.UpdateTaskPriorityOutputDto;
import com.example.taskManager.infrastructure.task.dtos.PatchTaskPriorityRequestDto;
import com.example.taskManager.infrastructure.task.dtos.PatchTaskPriorityResponseDto;

@Component
public class PatchTaskPriorityDtoMapper {
    
    public UpdateTaskPriorityInputDto toInput(PatchTaskPriorityRequestDto dto) {
        UpdateTaskPriorityInputDto input = new UpdateTaskPriorityInputDto();
        input.setPriority(dto.getPriority());
        return input;
    }

    public PatchTaskPriorityResponseDto toResponse(UpdateTaskPriorityOutputDto dto) {
        return new PatchTaskPriorityResponseDto(
            dto.getId(),
            dto.getTitle(),
            dto.getDescription(),
            dto.getCompleted(),
            dto.getPriority()
        );
    }

}