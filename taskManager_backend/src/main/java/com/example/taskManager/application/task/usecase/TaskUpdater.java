package com.example.taskManager.application.task.usecase;

import org.springframework.stereotype.Service;
import com.example.taskManager.application.task.dtos.UpdateTaskInputDto;
import com.example.taskManager.application.task.dtos.UpdateTaskOutputDto;
import com.example.taskManager.application.task.mappers.UpdateTaskApplicationMapper;
import com.example.taskManager.domain.task.exception.TaskNotFoundException;
import com.example.taskManager.domain.task.interfaces.TaskRepositoryPort;
import com.example.taskManager.domain.task.models.Task;
import jakarta.transaction.Transactional;

@Service
public class TaskUpdater {

    private final TaskRepositoryPort            taskRepositoryPort;
    private final UpdateTaskApplicationMapper   updateTaskApplicationMapper;

    public TaskUpdater(
        TaskRepositoryPort          taskRepositoryPort,
        UpdateTaskApplicationMapper updateTaskApplicationMapper
    ) {
        this.taskRepositoryPort =           taskRepositoryPort;
        this.updateTaskApplicationMapper =  updateTaskApplicationMapper;
    }

    @Transactional
    public UpdateTaskOutputDto execute(UpdateTaskInputDto inputDTO) {
        Integer userId = inputDTO.getUserId();
        Integer taskId = inputDTO.getId();
        Task task = taskRepositoryPort.findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        toggleCompletionAndPriority(task, userId);
        if (inputDTO.getPriority() != null) {
            updatePriority(task, inputDTO.getPriority(), userId);
        }

        return updateTaskApplicationMapper.toDTO(taskRepositoryPort.save(task));
    }

    private void toggleCompletionAndPriority(Task task, Integer userId) {
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
    }

    private void updatePriority(Task task, Integer newPriority, Integer userId) {
        Integer oldPriority = task.getPriority();
        if (newPriority.equals(oldPriority)) return;

        if (oldPriority < newPriority) {
            taskRepositoryPort.decrementPriorities(userId, oldPriority + 1, newPriority);
        } else {
            taskRepositoryPort.incrementPriorities(userId, newPriority, oldPriority - 1);
        }

        task.setPriority(newPriority);
    }

}