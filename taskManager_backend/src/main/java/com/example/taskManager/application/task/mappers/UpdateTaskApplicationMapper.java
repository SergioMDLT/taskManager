package com.example.taskManager.application.task.mappers;

import org.springframework.stereotype.Component;
import com.example.taskManager.application.task.dtos.UpdateTaskInputDto;
import com.example.taskManager.application.task.dtos.UpdateTaskOutputDto;
import com.example.taskManager.domain.task.models.Task;

@Component
public class UpdateTaskApplicationMapper {

    public Task toDomain(UpdateTaskInputDto dto, Integer userId) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(dto.getPriority());
        task.setUserId(userId);
        return task;
    }

    public UpdateTaskOutputDto toDTO(Task task) {
        UpdateTaskOutputDto dto = new UpdateTaskOutputDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setPriority(task.getPriority());
        dto.setCompleted(task.getCompleted());
        return dto;
    }

}