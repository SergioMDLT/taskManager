package com.example.taskManager.application.task.usecase;

import org.springframework.stereotype.Service;
import com.example.taskManager.application.task.dto.TaskResponseDTO;
import com.example.taskManager.domain.task.Task;
import com.example.taskManager.domain.task.TaskRepository;
import com.example.taskManager.infrastructure.task.adapter.TaskMapper;
import com.example.taskManager.infrastructure.task.exception.TaskNotFoundException;

@Service
public class GetTaskByIdUseCase {

    private final TaskRepository taskRepository;
    private final TaskMapper     taskMapper;

    public GetTaskByIdUseCase( TaskRepository taskRepository, TaskMapper taskMapper ) {
        this.taskRepository = taskRepository;
        this.taskMapper =     taskMapper;
    }

    public TaskResponseDTO execute( Integer id ) {
        Task task = taskRepository.findById( id )
            .orElseThrow(() -> new TaskNotFoundException( "Task not found with ID: " + id ));

        return taskMapper.toDTO( task );
    }

}
