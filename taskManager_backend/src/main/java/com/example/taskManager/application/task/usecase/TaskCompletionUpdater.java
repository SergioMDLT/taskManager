package com.example.taskManager.application.task.usecase;

import org.springframework.stereotype.Service;
import com.example.taskManager.application.task.dtos.UpdateTaskCompletionInputDto;
import com.example.taskManager.application.task.dtos.UpdateTaskCompletionOutputDto;
import com.example.taskManager.application.task.mappers.UpdateTaskCompletionApplicationMapper;
import com.example.taskManager.domain.task.exception.TaskNotFoundException;
import com.example.taskManager.domain.task.interfaces.TaskRepositoryPort;
import com.example.taskManager.domain.task.models.Task;
import jakarta.transaction.Transactional;

@Service
public class TaskCompletionUpdater {

    private final TaskRepositoryPort                    taskRepositoryPort;
    private final UpdateTaskCompletionApplicationMapper updateTaskCompletionApplicationMapper;

    public TaskCompletionUpdater(
        TaskRepositoryPort                      taskRepositoryPort,
        UpdateTaskCompletionApplicationMapper   updateTaskCompletionApplicationMapper
    ) {
        this.taskRepositoryPort =                       taskRepositoryPort;
        this.updateTaskCompletionApplicationMapper =    updateTaskCompletionApplicationMapper;
    }

    @Transactional
    public UpdateTaskCompletionOutputDto execute(UpdateTaskCompletionInputDto inputDTO) {
        Integer userId = inputDTO.getUserId();
        Task task = taskRepositoryPort.findByIdAndUserId(inputDTO.getId(), userId)
            .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        task.setUserId(userId);

        if (Boolean.TRUE.equals(task.getCompleted())) {
            Integer newPriority = taskRepositoryPort.findMaxPriorityByUser(userId).orElse(0) + 1;

            if (taskRepositoryPort.existsTaskWithPriority(userId, newPriority)) {
                throw new IllegalStateException("Task with priority " + newPriority + " already exists for user: " + userId);
            }

            task.setPriority(newPriority);
        } else {
            Integer removedPriority = task.getPriority();
            task.setPriority(null);

            if (removedPriority != null) {
                taskRepositoryPort.reducePrioritiesAfterCompletion(userId, removedPriority);
            }
        }

        task.setCompleted(!task.getCompleted());
        return updateTaskCompletionApplicationMapper.toDTO(taskRepositoryPort.save(task));
    }
    
}