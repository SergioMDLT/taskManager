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
        this.taskRepository =   taskRepository;
        this.taskMapper = taskMapper;
    }
    
    @Transactional
    public TaskResponseDTO execute( Integer taskId ) {
        Task task = taskRepository.findById( taskId )
            .orElseThrow(() -> new EntityNotFoundException( "Task not found" ));
        
        task.setCompleted( !task.getCompleted() );
        taskRepository.save( task );

        return taskMapper.toDTO( task );
    }

}
