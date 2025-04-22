package com.example.taskManager.infrastructure.task.mappers;

import com.example.taskManager.domain.task.models.Task;
import com.example.taskManager.infrastructure.task.entities.TaskEntity;
import com.example.taskManager.infrastructure.user.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskEntityMapper {

    public TaskEntity toEntity(Task task) {
        TaskEntity entity = new TaskEntity();
        entity.setId(task.getId());
        entity.setTitle(task.getTitle());
        entity.setDescription(task.getDescription());
        entity.setCompleted(task.getCompleted());
        entity.setPriority(task.getPriority());

        if (task.getUserId() != null) {
            UserEntity user = new UserEntity();
            user.setId(task.getUserId());
            entity.setUser(user);
        }

        return entity;
    }

    public Task toDomain(TaskEntity entity) {
        Task task = new Task();
        task.setId(entity.getId());
        task.setTitle(entity.getTitle());
        task.setDescription(entity.getDescription());
        task.setCompleted(entity.getCompleted());
        task.setPriority(entity.getPriority());

        if (entity.getUser() != null) {
            task.setUserId(entity.getUser().getId());
        }

        return task;
    }
    
}