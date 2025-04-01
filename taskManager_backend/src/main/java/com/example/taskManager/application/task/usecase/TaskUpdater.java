package com.example.taskManager.application.task.usecase;

import com.example.taskManager.domain.task.interfaces.TaskRepositoryPort;
import com.example.taskManager.infrastructure.task.dtos.TaskResponseDTO;
import com.example.taskManager.infrastructure.task.dtos.TaskUpdateRequestDTO;
import com.example.taskManager.infrastructure.task.entities.TaskEntity;
import com.example.taskManager.infrastructure.task.mappers.TaskMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

public class TaskUpdater {

    private final TaskRepositoryPort    taskRepositoryPort;
    private final TaskMapper            taskMapper;

    public TaskUpdater(TaskRepositoryPort taskRepositoryPort, TaskMapper taskMapper) {
        this.taskRepositoryPort =   taskRepositoryPort;
        this.taskMapper     =       taskMapper;
    }

    @Transactional
    public TaskResponseDTO execute(Integer taskId, TaskUpdateRequestDTO request) {
        TaskEntity task = taskRepositoryPort.findById(taskId)
            .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        String auth0Id = task.getUser().getAuth0Id();

        if (request.getCompleted() != null) {
            toggleCompletionAndPriority(task, auth0Id);
        }

        if (request.getPriority() != null) {
            updatePriority(task, request.getPriority(), auth0Id);
        }

        taskRepositoryPort.save(task);
        return taskMapper.toDTO(task);
    }

    private void toggleCompletionAndPriority(TaskEntity task, String auth0Id) {
        if (Boolean.TRUE.equals(task.getCompleted())) {
            Integer newPriority = taskRepositoryPort.findMaxPriorityByUser(auth0Id).orElse(0) + 1;

            if (taskRepositoryPort.existsTaskWithPriority(auth0Id, newPriority)) {
                throw new IllegalStateException("Task with priority " + newPriority + " already exists for user: " + auth0Id);
            }

            task.setPriority(newPriority);
        } else {
            Integer removedPriority = task.getPriority();
            task.setPriority(null);

            if (removedPriority != null) {
                taskRepositoryPort.reducePrioritiesAfterCompletion(auth0Id, removedPriority);
            }
        }

        task.setCompleted(!task.getCompleted());
    }

    private void updatePriority(TaskEntity task, Integer newPriority, String auth0Id) {
        Integer oldPriority = task.getPriority();
        if (newPriority.equals(oldPriority)) return;

        if (oldPriority < newPriority) {
            taskRepositoryPort.decrementPriorities(auth0Id, oldPriority + 1, newPriority);
        } else {
            taskRepositoryPort.incrementPriorities(auth0Id, newPriority, oldPriority - 1);
        }

        task.setPriority(newPriority);
    }

}