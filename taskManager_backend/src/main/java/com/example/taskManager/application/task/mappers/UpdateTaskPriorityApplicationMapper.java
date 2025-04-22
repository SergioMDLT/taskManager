package com.example.taskManager.application.task.mappers;

import org.springframework.stereotype.Component;
import com.example.taskManager.application.task.dtos.UpdateTaskPriorityInputDto;
import com.example.taskManager.application.task.dtos.UpdateTaskPriorityOutputDto;
import com.example.taskManager.domain.task.models.Task;

@Component
public class UpdateTaskPriorityApplicationMapper {

    public Task toDomain(UpdateTaskPriorityInputDto dto) {
        Task task = new Task();
        task.setId(dto.getId());
        task.setUserId(dto.getUserId());
        task.setPriority(dto.getPriority());
        return task;
    }

    public UpdateTaskPriorityOutputDto toDTO(Task task) {
        return new UpdateTaskPriorityOutputDto(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getCompleted(),
            task.getPriority()
        );
    }

}