package com.example.taskManager.mapper;

import org.springframework.stereotype.Component;
import com.example.taskManager.dto.TaskRequestDTO;
import com.example.taskManager.dto.TaskResponseDTO;
import com.example.taskManager.model.Task;

@Component
public class TaskMapper {
    public Task toEntity( TaskRequestDTO dto ) {
        Task task = new Task();
        task.setTitle( dto.getTitle() );
        task.setDescription( dto.getDescription() );
        task.setCompleted( dto.getCompleted() );
        task.setPriority( dto.getPriority() );
        return task;
    }

    public TaskResponseDTO toDTO( Task task ) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId( task.getId() );
        dto.setTitle( task.getTitle() );
        dto.setDescription( task.getDescription() );
        dto.setCompleted( task.getCompleted() );
        dto.setPriority( task.getPriority() );
        return dto;
    }
}

