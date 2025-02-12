package com.example.taskManager.service;

import com.example.taskManager.model.Task;
import com.example.taskManager.repository.TaskRepository;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService implements ITaskService{

    private final TaskRepository taskRepository;
    public TaskService( TaskRepository taskRepository ) {
         this.taskRepository = taskRepository;
    }
    
    @Override
    public Page<Task> getAllTasks( Pageable pageable ) {
        return taskRepository.findAll( pageable );
    }

    @Override
    public Task getTaskById( Integer id ) {
        return taskRepository.findById( id ).orElse(null);
    }

    @Override
    public Page<Task> getTasksByTitle( String title, Pageable pageable ) {
        return taskRepository.findByTitleContainingIgnoreCase( title, pageable );
    }

    @Override
    public Page<Task> getTasksByCompleted( Boolean completed, Pageable pageable ) {
        return taskRepository.findByCompleted( completed, pageable );
    }

    @Override
    public Page<Task> getTasksByCompletedAndTitle( Boolean completed, String title, Pageable pageable ) {
        return taskRepository.findByCompletedAndTitleContainingIgnoreCase( completed, title, pageable );
    }

    @Override
    public Task createTask( Task task ) {
        if ( task.getPriority() == null ) {
            Integer maxPriority = taskRepository.findMaxPriority().orElse(0);
            task.setPriority( maxPriority + 1 );
        }
       return taskRepository.save( task );
    }

    @Override
    public Task updateTaskStatus( Integer id ) {
        Task task = taskRepository.findById( id )
            .orElseThrow(() -> new IllegalArgumentException( "Task not found with ID: " + id ));

        boolean wasCompleted = task.getCompleted();
        task.setCompleted( !wasCompleted );

        if ( !wasCompleted ) {
            task.setPriority( 0 );
        } else {
            Integer highestPriority = taskRepository.findMaxPriority()
                .orElse(0);
            task.setPriority( highestPriority + 1 );
        }

        return taskRepository.save( task );
    }


    @Override
    public void deleteTaskById( Integer id ) {
        taskRepository.deleteById( id );
    }
    
    @Transactional
    public void updatePriority( Integer id, Integer newPriority ) {
        Task task = taskRepository.findById( id )
            .orElseThrow(() -> new IllegalArgumentException( "Task not found with ID: " + id ));

        Integer oldPriority = task.getPriority();

        if ( newPriority > oldPriority ) {
            taskRepository.updatePrioritiesDown( oldPriority + 1, newPriority );
        }
        else if ( newPriority < oldPriority ) {
            taskRepository.updatePrioritiesUp( newPriority, oldPriority - 1 );
        }

        task.setPriority( newPriority );
        taskRepository.save( task );
    }
}
