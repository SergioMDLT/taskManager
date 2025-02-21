package com.example.taskManager.task.application.query;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.taskManager.task.application.dto.TaskResponseDTO;
import com.example.taskManager.task.domain.Task;
import com.example.taskManager.task.domain.TaskRepository;
import com.example.taskManager.task.infrastructure.adapter.TaskMapper;
import com.example.taskManager.task.infrastructure.exception.TaskNotFoundException;

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
        Integer userId,
        String title,
        Boolean completed,
        int page,
        int size,
        String sort
    ) {
        // Si se busca por completed, ordenar por priority DESC, si no, ordenar por el campo solicitado
        Sort sorting = (completed != null) 
            ? Sort.by(Sort.Direction.DESC, "priority") 
            : Sort.by(Sort.Direction.ASC, sort);
    
        Pageable pageable = PageRequest.of(page, size, sorting);
    
        if (userId == null) {
            throw new IllegalArgumentException("El userId es obligatorio para filtrar tareas por usuario");
        }
    
        if (id != null) {
            Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));
            return new PageImpl<>(List.of(taskMapper.toDTO(task)), pageable, 1);
        }
    
        if (completed != null && title != null) {
            return taskRepository.findByUserIdAndCompletedAndTitleContainingIgnoreCase(userId, completed, title, pageable)
                .map(taskMapper::toDTO);
        }
    
        if (completed != null) {
            return taskRepository.findByUserIdAndCompleted(userId, completed, pageable)
                .map(taskMapper::toDTO);
        }
    
        if (title != null) {
            return taskRepository.findByUserIdAndTitleContainingIgnoreCase(userId, title, pageable)
                .map(taskMapper::toDTO);
        }
    
        return taskRepository.findAll(pageable).map(taskMapper::toDTO);
    }


}
