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
public class GetTasksByUserAndTitleAndCompletionStatusUseCase {

    private final TaskRepository taskRepository;
    private final TaskMapper     taskMapper;

    public GetTasksByUserAndTitleAndCompletionStatusUseCase( TaskRepository taskRepository, TaskMapper taskMapper ) {
        this.taskRepository = taskRepository;
        this.taskMapper =     taskMapper;
    }

    public Page<TaskResponseDTO> execute( String auth0Id, Boolean completed, String title, int page, int size ) {
        Pageable pageable = PageRequest.of( page, size, Sort.by( Sort.Direction.ASC, "priority" ));
        return taskRepository.findByUser_Auth0IdAndCompletedAndTitleContainingIgnoreCase( auth0Id, completed, title, pageable )
                .map( taskMapper::toDTO );
    }

}
