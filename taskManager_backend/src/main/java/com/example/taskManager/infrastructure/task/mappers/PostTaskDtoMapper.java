package com.example.taskManager.infrastructure.task.mappers;

import org.springframework.stereotype.Component;
import com.example.taskManager.application.task.dtos.CreateTaskInputDto;
import com.example.taskManager.application.task.dtos.CreateTaskOutputDto;
import com.example.taskManager.infrastructure.task.dtos.CreateTaskRequestDto;
import com.example.taskManager.infrastructure.task.dtos.CreateTaskResponseDto;

@Component
public class PostTaskDtoMapper {
    
    public CreateTaskInputDto toInput(CreateTaskRequestDto dto, Integer userId) {
        CreateTaskInputDto input = new CreateTaskInputDto();
        input.setUserId(userId);
        input.setTitle(dto.getTitle());
        input.setDescription(dto.getDescription());
        input.setPriority(dto.getPriority());
        return input;
    }

    public CreateTaskResponseDto toResponse(CreateTaskOutputDto output) {
        CreateTaskResponseDto dto = new CreateTaskResponseDto();
        dto.setId(output.getId());
        dto.setTitle(output.getTitle());
        dto.setDescription(output.getDescription());
        dto.setCompleted(output.getCompleted());
        dto.setPriority(output.getPriority());
        return dto;
    }

}