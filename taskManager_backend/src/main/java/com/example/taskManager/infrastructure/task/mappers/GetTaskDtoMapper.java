package com.example.taskManager.infrastructure.task.mappers;

import org.springframework.stereotype.Component;
import com.example.taskManager.application.task.dtos.GetTaskOutputDto;
import com.example.taskManager.infrastructure.task.dtos.GetTaskResponseDto;

@Component
public class GetTaskDtoMapper {

    public GetTaskResponseDto toResponse(GetTaskOutputDto output) {
        GetTaskResponseDto dto = new GetTaskResponseDto();
        dto.setId(output.getId());
        dto.setTitle(output.getTitle());
        dto.setDescription(output.getDescription());
        dto.setCompleted(output.getCompleted());
        dto.setPriority(output.getPriority());
        return dto;
    }
    
}