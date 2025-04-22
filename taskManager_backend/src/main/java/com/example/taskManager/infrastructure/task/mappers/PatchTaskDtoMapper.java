package com.example.taskManager.infrastructure.task.mappers;

import org.springframework.stereotype.Component;
import com.example.taskManager.application.task.dtos.UpdateTaskInputDto;
import com.example.taskManager.application.task.dtos.UpdateTaskOutputDto;
import com.example.taskManager.infrastructure.task.dtos.PatchTaskRequestDto;
import com.example.taskManager.infrastructure.task.dtos.PatchTaskResponseDto;

@Component
public class PatchTaskDtoMapper {

    public UpdateTaskInputDto toInput(PatchTaskRequestDto dto, Integer userId) {
        UpdateTaskInputDto input = new UpdateTaskInputDto();
        input.setUserId(userId);
        input.setTitle(dto.getTitle());
        input.setDescription(dto.getDescription());
        input.setPriority(dto.getPriority());
        return input;
    }

    public PatchTaskResponseDto toResponse(UpdateTaskOutputDto output) {
        PatchTaskResponseDto dto = new PatchTaskResponseDto();
        dto.setId(output.getId());
        dto.setTitle(output.getTitle());
        dto.setDescription(output.getDescription());
        dto.setCompleted(output.getCompleted());
        dto.setPriority(output.getPriority());
        return dto;
    }
    
}