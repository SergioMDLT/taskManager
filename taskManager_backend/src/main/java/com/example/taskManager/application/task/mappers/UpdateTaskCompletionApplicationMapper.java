package com.example.taskManager.application.task.mappers;

import org.springframework.stereotype.Component;
import com.example.taskManager.application.task.dtos.UpdateTaskCompletionInputDto;
import com.example.taskManager.application.task.dtos.UpdateTaskCompletionOutputDto;
import com.example.taskManager.domain.task.models.Task;

@Component
public class UpdateTaskCompletionApplicationMapper {
    
    public Task toDomain(UpdateTaskCompletionInputDto dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setUserId(dto.getUserId());
        return task;
    }

    public UpdateTaskCompletionOutputDto toDTO(Task task) {
        return new UpdateTaskCompletionOutputDto(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getCompleted(),
            task.getPriority()
        );
    }
    
}
