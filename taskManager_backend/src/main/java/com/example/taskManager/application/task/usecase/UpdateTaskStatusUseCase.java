package com.example.taskManager.application.task.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taskManager.application.task.dto.TaskResponseDTO;
import com.example.taskManager.domain.task.Task;
import com.example.taskManager.domain.task.TaskRepository;
import com.example.taskManager.infrastructure.task.adapter.TaskMapper;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UpdateTaskStatusUseCase {

    private final TaskRepository taskRepository;
    private final TaskMapper     taskMapper;

    public UpdateTaskStatusUseCase( TaskRepository taskRepository, TaskMapper taskMapper ) {
        this.taskRepository = taskRepository;
        this.taskMapper =     taskMapper;
    }
    
    @Transactional
    public TaskResponseDTO execute( Integer taskId ) {
        Task task = taskRepository.findById( taskId )
            .orElseThrow(() -> new EntityNotFoundException( "Task not found" ));

        String auth0Id = task.getUser().getAuth0Id();

        if ( Boolean.TRUE.equals( task.getCompleted() )) {
            Integer newPriority = taskRepository.findMaxPriorityByUser_Auth0Id( auth0Id ).orElse( 0 ) + 1;

            if ( taskRepository.existsTaskWithPriority( auth0Id, newPriority )) {
                throw new IllegalStateException( "Task with priority " + newPriority + " already exists for user: " + auth0Id );
            }

            task.setPriority( newPriority );
        } else {
            Integer removedPriority = task.getPriority();
            task.setPriority( null );

            if ( removedPriority != null ) {
                taskRepository.reducePrioritiesAfterCompletion( auth0Id, removedPriority );
            }
        }

        task.setCompleted( !task.getCompleted() );
        taskRepository.save( task );

        return taskMapper.toDTO( task );
    }

}
