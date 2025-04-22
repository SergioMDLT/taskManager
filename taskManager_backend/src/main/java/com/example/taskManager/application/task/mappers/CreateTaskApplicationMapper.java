package com.example.taskManager.application.task.mappers;

import org.springframework.stereotype.Component;
import com.example.taskManager.application.task.dtos.CreateTaskInputDto;
import com.example.taskManager.application.task.dtos.CreateTaskOutputDto;
import com.example.taskManager.domain.task.models.Task;

@Component
public class CreateTaskApplicationMapper {
    
    public Task toDomain(CreateTaskInputDto dto, Integer userId) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setCompleted(false);
        task.setUserId(userId);
        return task;
    }

    public CreateTaskOutputDto toDTO(Task task) {
        CreateTaskOutputDto dto = new CreateTaskOutputDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setPriority(task.getPriority());
        dto.setCompleted(task.getCompleted());
        return dto;
    }

}