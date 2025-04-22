package com.example.taskManager.application.task.usecase;

import org.springframework.stereotype.Service;
import com.example.taskManager.application.task.dtos.GetTaskOutputDto;
import com.example.taskManager.application.task.mappers.GetTaskApplicationMapper;
import com.example.taskManager.domain.task.exception.TaskNotFoundException;
import com.example.taskManager.domain.task.interfaces.TaskRepositoryPort;
import com.example.taskManager.domain.task.models.Task;

@Service
public class TaskGetterById {

    private final GetTaskApplicationMapper  getTaskApplicationMapper;
    private final TaskRepositoryPort        taskRepositoryPort;

    public TaskGetterById(
        GetTaskApplicationMapper    getTaskApplicationMapper,
        TaskRepositoryPort          taskRepositoryPort
    ) {
        this.getTaskApplicationMapper = getTaskApplicationMapper;
        this.taskRepositoryPort =       taskRepositoryPort;
    }

    public GetTaskOutputDto execute(Integer id) {
        Task task = taskRepositoryPort.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));

        return getTaskApplicationMapper.toDTO(task);
    }

}