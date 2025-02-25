package com.example.taskManager.application.task.usecase;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.example.taskManager.domain.task.Task;
import com.example.taskManager.domain.task.TaskRepository;
import com.example.taskManager.infrastructure.task.exception.TaskNotFoundException;

@Service
public class DeleteTaskUseCase {

    private final TaskRepository taskRepository;

    public DeleteTaskUseCase( TaskRepository taskRepository ) {
        this.taskRepository =   taskRepository;
    }
 
    public void execute( Integer taskId, String auth0Id ) {
        Task task = taskRepository.findById( taskId )
            .orElseThrow(() -> new TaskNotFoundException( "Task not found" ));
    
        if ( !task.getUser().getAuth0Id().equals( auth0Id )) {
            throw new AccessDeniedException( "You can not delete this task" );
        }
    
        taskRepository.delete( task );
    }
    
}
