package com.example.taskManager.infrastructure.task.mappers;

import org.springframework.stereotype.Component;
import com.example.taskManager.infrastructure.task.dtos.TaskRequestDTO;
import com.example.taskManager.infrastructure.task.dtos.TaskResponseDTO;
import com.example.taskManager.infrastructure.task.entities.TaskEntity;
import com.example.taskManager.infrastructure.user.entities.User;

@Component
public class TaskMapper {

    public TaskEntity toEntity( TaskRequestDTO dto ) {
        TaskEntity task = new TaskEntity();
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

    public TaskResponseDTO toDTO( TaskEntity task ) {
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
