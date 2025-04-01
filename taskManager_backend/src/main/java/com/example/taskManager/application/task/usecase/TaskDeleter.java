package com.example.taskManager.application.task.usecase;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.example.taskManager.domain.task.interfaces.TaskRepositoryPort;
import com.example.taskManager.infrastructure.task.entities.TaskEntity;
import com.example.taskManager.infrastructure.task.exception.TaskNotFoundException;

@Service
public class TaskDeleter {

    private final TaskRepositoryPort taskRepositoryPort;

    public TaskDeleter( TaskRepositoryPort taskRepositoryPort ) {
        this.taskRepositoryPort =   taskRepositoryPort;
    }
 
    public void execute( Integer taskId, String auth0Id ) {
        TaskEntity task = taskRepositoryPort.findById( taskId )
            .orElseThrow(() -> new TaskNotFoundException( "Task not found" ));
    
        if ( !task.getUser().getAuth0Id().equals( auth0Id )) {
            throw new AccessDeniedException( "You can not delete this task" );
        }
    
        taskRepositoryPort.delete( task );
    }
    
}