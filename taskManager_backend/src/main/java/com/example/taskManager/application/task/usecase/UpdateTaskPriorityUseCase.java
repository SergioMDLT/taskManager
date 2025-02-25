package com.example.taskManager.application.task.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taskManager.domain.task.Task;
import com.example.taskManager.domain.task.TaskRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UpdateTaskPriorityUseCase {

    private final TaskRepository taskRepository;

    public UpdateTaskPriorityUseCase( TaskRepository taskRepository ) {
        this.taskRepository = taskRepository;
    }
    
    @Transactional
    public void execute( Integer taskId, Integer newPriority ) {
        Task task = taskRepository.findById( taskId )
            .orElseThrow(() -> new EntityNotFoundException( "Task not found" ));

        if ( !newPriority.equals( task.getPriority() )) {
            updatePriority( taskId, newPriority );
        }
    }

    @Transactional
    private void updatePriority( Integer taskId, Integer newPriority ) {
        // Implementa aquí la lógica para cambiar la prioridad y reorganizar las tareas
    }
}
