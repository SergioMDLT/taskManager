package com.example.taskManager.application.task.usecase;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.example.taskManager.domain.task.exception.TaskNotFoundException;
import com.example.taskManager.domain.task.interfaces.TaskRepositoryPort;
import com.example.taskManager.domain.task.models.Task;
import jakarta.transaction.Transactional;

@Service
public class TaskDeleter {

    private final TaskRepositoryPort taskRepositoryPort;

    public TaskDeleter(TaskRepositoryPort taskRepositoryPort) {
        this.taskRepositoryPort = taskRepositoryPort;
    }

    @Transactional
    public void execute(Integer taskId, Integer userId) {
        Task task = taskRepositoryPort.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        Integer removedPriority = task.getPriority();
    
        if (!task.getUserId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to delete this task");
        }
    
        taskRepositoryPort.delete(task);

        if (removedPriority != null) {
            taskRepositoryPort.decrementPriorities(userId, removedPriority + 1, Integer.MAX_VALUE);
        }
    }

}