package com.example.taskManager.application.task.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.taskManager.application.task.dto.TaskResponseDTO;
import com.example.taskManager.domain.task.TaskRepository;
import com.example.taskManager.infrastructure.task.adapter.TaskMapper;

@Service
public class GetTasksByUserUseCase {

    private final TaskRepository taskRepository;
    private final TaskMapper     taskMapper;

    public GetTasksByUserUseCase( TaskRepository taskRepository, TaskMapper taskMapper ) {
        this.taskRepository = taskRepository;
        this.taskMapper =     taskMapper;
    }

    public Page<TaskResponseDTO> execute( String auth0Id, int page, int size, String sort ) {
        if ( auth0Id == null ) {
            throw new IllegalArgumentException( "Auth0Id required to filter tasks by user" );
        }

        Pageable pageable = PageRequest.of( page, size, Sort.by(Sort.Direction.ASC, sort ));
        return taskRepository.findByUser_Auth0Id( auth0Id, pageable ).map( taskMapper::toDTO );
    }
    
}
