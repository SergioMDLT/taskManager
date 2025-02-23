package com.example.taskManager.task.infrastructure.adapter;

import org.springframework.stereotype.Component;
import com.example.taskManager.task.application.dto.TaskRequestDTO;
import com.example.taskManager.task.application.dto.TaskResponseDTO;
import com.example.taskManager.task.domain.Task;
import com.example.taskManager.user.domain.User;

@Component
public class TaskMapper {
    public Task toEntity( TaskRequestDTO dto ) {
        Task task = new Task();
        task.setTitle( dto.getTitle() );
        task.setDescription( dto.getDescription() );
        task.setCompleted( dto.getCompleted() );
        task.setPriority( dto.getPriority() );
        if ( dto.getUserId() != null ) {
            User user = new User();
            user.setId( dto.getUserId() );
            task.setUser( user );
        }
        return task;
    }

    public TaskResponseDTO toDTO( Task task ) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId( task.getId() );
        dto.setTitle( task.getTitle() );
        dto.setDescription( task.getDescription() );
        dto.setCompleted( task.getCompleted() );
        dto.setPriority( task.getPriority() );
        if ( task.getUser() != null ) {
            dto.setUserId( task.getUser().getId() );
        }
        return dto;
    }
}

