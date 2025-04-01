package com.example.taskManager.application.task.usecase;

import org.springframework.stereotype.Service;
import com.example.taskManager.domain.task.interfaces.TaskRepositoryPort;
import com.example.taskManager.infrastructure.task.dtos.TaskResponseDTO;
import com.example.taskManager.infrastructure.task.entities.TaskEntity;
import com.example.taskManager.infrastructure.task.exception.TaskNotFoundException;
import com.example.taskManager.infrastructure.task.mappers.TaskMapper;

@Service
public class TaskGetterById {

    private final TaskRepositoryPort    taskRepositoryPort;
    private final TaskMapper            taskMapper;

    public TaskGetterById( TaskRepositoryPort taskRepositoryPort, TaskMapper taskMapper ) {
        this.taskRepositoryPort = taskRepositoryPort;
        this.taskMapper =     taskMapper;
    }

    public TaskResponseDTO execute( Integer id ) {
        TaskEntity task = taskRepositoryPort.findById( id )
            .orElseThrow(() -> new TaskNotFoundException( "Task not found with ID: " + id ));

        return taskMapper.toDTO( task );
    }

}