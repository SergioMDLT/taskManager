package com.example.taskManager.application.task.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taskManager.application.task.dtos.CreateTaskInputDto;
import com.example.taskManager.application.task.dtos.CreateTaskOutputDto;
import com.example.taskManager.application.task.mappers.CreateTaskApplicationMapper;
import com.example.taskManager.domain.task.interfaces.TaskRepositoryPort;
import com.example.taskManager.domain.task.models.Task;

@Service
public class TaskCreator {

    private final CreateTaskApplicationMapper   createTaskApplicationMapper;
    private final TaskRepositoryPort            taskRepositoryPort;

    public TaskCreator(
            CreateTaskApplicationMapper createTaskApplicationMapper,
            TaskRepositoryPort          taskRepositoryPort
        ) {
            this.createTaskApplicationMapper =  createTaskApplicationMapper;
            this.taskRepositoryPort =           taskRepositoryPort;
    }

    @Transactional
    public CreateTaskOutputDto execute(CreateTaskInputDto inputDTO) {
        Integer userId = inputDTO.getUserId();
        Task task = createTaskApplicationMapper.toDomain(inputDTO, userId);
        Integer assignedPriority = task.getPriority();

        if (assignedPriority == null) {
            assignedPriority = taskRepositoryPort.findMaxPriorityByUser(userId).orElse(0) + 1;
        } else {
            if (taskRepositoryPort.existsTaskWithPriority(userId, assignedPriority)) {
                throw new IllegalStateException("User already has a task with priority: " + assignedPriority);
            }
        }

        task.setPriority(assignedPriority);
        return createTaskApplicationMapper.toDTO(taskRepositoryPort.save(task));
    }

}