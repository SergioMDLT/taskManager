package com.example.taskManager.application.task.mappers;

import org.springframework.stereotype.Component;
import com.example.taskManager.application.task.dtos.GetTaskOutputDto;
import com.example.taskManager.domain.task.models.Task;

@Component
public class GetTaskApplicationMapper {

    public GetTaskOutputDto toDTO(Task task) {
        GetTaskOutputDto dto = new GetTaskOutputDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.getCompleted());
        dto.setPriority(task.getPriority());
        return dto;
    }

}