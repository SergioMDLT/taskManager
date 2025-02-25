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
public class GetTasksByUserAndTitleUseCase {

    private final TaskRepository taskRepository;
    private final TaskMapper     taskMapper;

    public GetTasksByUserAndTitleUseCase( TaskRepository taskRepository, TaskMapper taskMapper ) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public Page<TaskResponseDTO> execute( String auth0Id, String title, int page, int size ) {
        Pageable pageable = PageRequest.of( page, size, Sort.by( Sort.Direction.ASC, "title" ));
        return taskRepository.findByUser_Auth0IdAndTitleContainingIgnoreCase( auth0Id, title, pageable )
                .map( taskMapper::toDTO );
    }
    
}
