package com.example.taskManager.application.task.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.taskManager.domain.task.interfaces.TaskRepositoryPort;
import com.example.taskManager.infrastructure.task.dtos.TaskResponseDTO;
import com.example.taskManager.infrastructure.task.dtos.TaskSearchFiltersDTO;
import com.example.taskManager.infrastructure.task.mappers.TaskMapper;

@Service
public class TaskSearcher {

    private final TaskRepositoryPort    taskRepositoryPort;
    private final TaskMapper            taskMapper;

    public TaskSearcher(TaskRepositoryPort taskRepositoryPort, TaskMapper taskMapper) {
        this.taskRepositoryPort =   taskRepositoryPort;
        this.taskMapper =           taskMapper;
    }

    public Page<TaskResponseDTO> search(TaskSearchFiltersDTO filters) {
        Pageable pageable = PageRequest.of(filters.getPage(), filters.getSize(), Sort.by(Sort.Direction.ASC, filters.getSort()));

        if (filters.getTitle() != null && filters.getCompleted() != null) {
            return taskRepositoryPort
                    .findByUserIdAndCompletedAndTitle(
                            filters.getAuth0Id(), filters.getCompleted(), filters.getTitle(), pageable)
                    .map(taskMapper::toDTO);

        } else if (filters.getCompleted() != null) {
            return taskRepositoryPort
                    .findByUserIdAndCompleted(filters.getAuth0Id(), filters.getCompleted(), pageable)
                    .map(taskMapper::toDTO);

        } else if (filters.getTitle() != null) {
            return taskRepositoryPort
                    .findByUserIdAndTitle(filters.getAuth0Id(), filters.getTitle(), pageable)
                    .map(taskMapper::toDTO);

        } else {
            return taskRepositoryPort
                    .findByUserId(filters.getAuth0Id(), pageable)
                    .map(taskMapper::toDTO);
        }
    }

}