package com.example.taskManager.service.query;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.taskManager.dto.TaskResponseDTO;
import com.example.taskManager.exception.TaskNotFoundException;
import com.example.taskManager.mapper.TaskMapper;
import com.example.taskManager.model.Task;
import com.example.taskManager.repository.TaskRepository;

@Service
public class TaskQueryService implements ITaskQueryService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskQueryService( TaskRepository taskRepository, TaskMapper taskMapper ) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public Page<TaskResponseDTO> getTasks(
        Integer id,
        String title,
        Boolean completed,
        int page,
        int size,
        String sort
    ) {
        Pageable pageable = PageRequest.of( page, size, Sort.by( sort ));
        
        if (id != null) {
            Task task = taskRepository.findById( id )
                .orElseThrow(() -> new TaskNotFoundException( "Task not found with ID: " + id ));
            return new PageImpl<>( List.of(taskMapper.toDTO( task )), pageable, 1 );
        }

        if ( completed != null && title != null ) {
            return taskRepository.findByCompletedAndTitleContainingIgnoreCase( completed, title, pageable )
                .map(taskMapper::toDTO);
        }

        if (completed != null) {
            return taskRepository.findByCompleted( completed, pageable ).map( taskMapper::toDTO );
        }

        if (title != null) {
            return taskRepository.findByTitleContainingIgnoreCase( title, pageable ).map( taskMapper::toDTO );
        }

        return taskRepository.findAll( pageable ).map( taskMapper::toDTO );
    }
}
