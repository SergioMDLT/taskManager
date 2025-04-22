package com.example.taskManager.application.task.usecase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.taskManager.application.task.dtos.SearchTaskOutputDto;
import com.example.taskManager.application.task.dtos.TaskSearchFiltersDTO;
import com.example.taskManager.application.task.mappers.SearchTaskApplicationMapper;
import com.example.taskManager.domain.task.interfaces.TaskRepositoryPort;

@Service
public class TaskSearcher {

    private final SearchTaskApplicationMapper   searchTaskApplicationMapper;
    private final TaskRepositoryPort            taskRepositoryPort;

    public TaskSearcher(
        SearchTaskApplicationMapper searchTaskApplicationMapper,
        TaskRepositoryPort          taskRepositoryPort
    ) {
        this.searchTaskApplicationMapper =  searchTaskApplicationMapper;
        this.taskRepositoryPort =           taskRepositoryPort;
    }

    public Page<SearchTaskOutputDto> search(TaskSearchFiltersDTO filters) {
        Pageable pageable = PageRequest.of(filters.getPage(), filters.getSize(), Sort.by(Sort.Direction.ASC, filters.getSort()));

        if (filters.getTitle() != null && filters.getCompleted() != null) {
            return taskRepositoryPort
                    .findByUserIdAndCompletedAndTitle(filters.getUserId(), filters.getCompleted(), filters.getTitle(), pageable)
                    .map(searchTaskApplicationMapper::toDto);
        } else if (filters.getCompleted() != null) {
            return taskRepositoryPort
                    .findByUserIdAndCompleted(filters.getUserId(), filters.getCompleted(), pageable)
                    .map(searchTaskApplicationMapper::toDto);
        } else if (filters.getTitle() != null) {
            return taskRepositoryPort
                    .findByUserIdAndTitle(filters.getUserId(), filters.getTitle(), pageable)
                    .map(searchTaskApplicationMapper::toDto);
        } else {
            return taskRepositoryPort
                    .findByUserId(filters.getUserId(), pageable)
                    .map(searchTaskApplicationMapper::toDto);
        }
    }

}