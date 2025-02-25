package com.example.taskManager.infrastructure.task.adapter;

import org.springframework.stereotype.Component;
import com.example.taskManager.application.task.dto.TaskRequestDTO;
import com.example.taskManager.application.task.dto.TaskResponseDTO;
import com.example.taskManager.domain.task.Task;
import com.example.taskManager.domain.user.User;

@Component
public class TaskMapper {

    public Task toEntity( TaskRequestDTO dto ) {
        Task task = new Task();
        task.setTitle( dto.getTitle() );
        task.setDescription( dto.getDescription() );
        task.setCompleted( dto.getCompleted() );
        task.setPriority( dto.getPriority() );

        if ( dto.getAuth0Id() != null ) {
            User user = new User();
            user.setAuth0Id( dto.getAuth0Id() );
            task.setUser( user );
        }

        return task;
    }

    public TaskResponseDTO toDTO( Task task ) {
        return new TaskResponseDTO(
            task.getId(),
            task.getUser() != null ? task.getUser().getAuth0Id() : null,
            task.getTitle(),
            task.getDescription(),
            task.getCompleted(),
            task.getPriority()
        );
    }

}
