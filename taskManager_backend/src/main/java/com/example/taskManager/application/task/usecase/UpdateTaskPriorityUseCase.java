package com.example.taskManager.application.task.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taskManager.domain.task.Task;
import com.example.taskManager.domain.task.TaskRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UpdateTaskPriorityUseCase {

    private final TaskRepository taskRepository;

    public UpdateTaskPriorityUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void execute( Integer taskId, Integer newPriority ) {
        Task task = taskRepository.findById( taskId )
            .orElseThrow(() -> new EntityNotFoundException( "Task not found" ));

        Integer currentPriority = task.getPriority();
        String auth0Id = task.getUser().getAuth0Id();

        if ( newPriority.equals( currentPriority )) {
            return;
        }

        if ( taskRepository.existsTaskWithPriority( auth0Id, newPriority )) {
            throw new IllegalStateException( "User already has a task with this priority" );
        }

        if ( newPriority > currentPriority ) {
            taskRepository.shiftPrioritiesDown( auth0Id, taskId, newPriority );
        } else {
            taskRepository.shiftPrioritiesUp( auth0Id, taskId, newPriority );
        }

        task.setPriority( newPriority );
        taskRepository.save( task );
    }

}