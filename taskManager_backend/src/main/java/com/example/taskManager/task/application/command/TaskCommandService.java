package com.example.taskManager.task.application.command;

import org.springframework.stereotype.Service;

import com.example.taskManager.task.application.dto.TaskRequestDTO;
import com.example.taskManager.task.application.dto.TaskResponseDTO;
import com.example.taskManager.task.domain.Task;
import com.example.taskManager.task.domain.TaskRepository;
import com.example.taskManager.task.infrastructure.adapter.TaskMapper;
import com.example.taskManager.task.infrastructure.exception.TaskNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class TaskCommandService implements ITaskCommandService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskCommandService( TaskRepository taskRepository, TaskMapper taskMapper ) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskResponseDTO createTask( TaskRequestDTO taskRequestDTO ) {
        Task task = taskMapper.toEntity( taskRequestDTO );
        if ( task.getPriority() == null ) {
            Integer maxPriority = taskRepository.findMaxPriority().orElse(0);
            task.setPriority(maxPriority + 1);
        }
        return taskMapper.toDTO( taskRepository.save( task ));
    }

    @Override
    public TaskResponseDTO updateTaskStatus( Integer id ) {
        Task task = taskRepository.findById( id )
            .orElseThrow(() -> new TaskNotFoundException( "Task not found with ID: " + id ));

        task.setCompleted( !task.getCompleted() );

        if ( !task.getCompleted() ) {
            task.setPriority( null );
        } else {
            Integer maxPriority = taskRepository.findMaxPriority().orElse( 0 );
            task.setPriority( maxPriority + 1 );
        }

        return taskMapper.toDTO( taskRepository.save( task ));
    }

    @Override
    @Transactional
    public void updatePriority( Integer id, Integer newPriority ) {
        Task task = taskRepository.findById( id )
            .orElseThrow(() -> new TaskNotFoundException( "Task not found with ID: " + id ));

        Integer oldPriority = task.getPriority();

        if ( newPriority > oldPriority ) {
            taskRepository.updatePrioritiesDown( oldPriority + 1, newPriority );
        } else if ( newPriority < oldPriority ) {
            taskRepository.updatePrioritiesUp( newPriority, oldPriority - 1 );
        }

        task.setPriority( newPriority );
        taskRepository.save( task );
    }

    @Override
    public void deleteTaskById( Integer id ) {
        if ( !taskRepository.existsById( id )) {
            throw new TaskNotFoundException( "Task not found with ID: " + id );
        }
        taskRepository.deleteById( id );
    }
}
