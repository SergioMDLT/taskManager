package com.example.taskManager.application.task.usecase;

import org.springframework.stereotype.Service;

import com.example.taskManager.application.task.dtos.UpdateTaskPriorityInputDto;
import com.example.taskManager.application.task.dtos.UpdateTaskPriorityOutputDto;
import com.example.taskManager.application.task.mappers.UpdateTaskPriorityApplicationMapper;
import com.example.taskManager.domain.task.exception.TaskNotFoundException;
import com.example.taskManager.domain.task.interfaces.TaskRepositoryPort;
import com.example.taskManager.domain.task.models.Task;
import jakarta.transaction.Transactional;

@Service
public class TaskPriorityUpdater {

    private final TaskRepositoryPort                    taskRepositoryPort;
    private final UpdateTaskPriorityApplicationMapper   updateTaskPriorityApplicationMapper;

    public TaskPriorityUpdater(
        TaskRepositoryPort                  taskRepositoryPort,
        UpdateTaskPriorityApplicationMapper updateTaskPriorityApplicationMapper
    ) {
        this.taskRepositoryPort =                   taskRepositoryPort;
        this.updateTaskPriorityApplicationMapper =  updateTaskPriorityApplicationMapper;
    }

    @Transactional
    public UpdateTaskPriorityOutputDto execute(UpdateTaskPriorityInputDto input) {
        Task task = taskRepositoryPort.findById(input.getId())
            .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        Integer oldPriority = task.getPriority();
        Integer newPriority = input.getPriority();
        Integer userId = input.getUserId();

        if (oldPriority == null || newPriority.equals(oldPriority)) {
            task.setPriority(newPriority);
        } else if (oldPriority < newPriority) {
            taskRepositoryPort.decrementPriorities(userId, oldPriority + 1, newPriority);
            task.setPriority(newPriority);
        } else {
            taskRepositoryPort.incrementPriorities(userId, newPriority, oldPriority - 1);
            task.setPriority(newPriority);
        }

        return updateTaskPriorityApplicationMapper.toDTO(taskRepositoryPort.save(task));
    }
    
}