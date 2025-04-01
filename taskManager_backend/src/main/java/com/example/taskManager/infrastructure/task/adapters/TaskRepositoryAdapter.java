package com.example.taskManager.infrastructure.task.adapters;

import com.example.taskManager.domain.task.interfaces.TaskRepositoryPort;
import com.example.taskManager.infrastructure.task.entities.TaskEntity;
import com.example.taskManager.infrastructure.task.repositories.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final TaskRepository taskRepository;

    public TaskRepositoryAdapter(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Optional<TaskEntity> findById(Integer id) {
        return taskRepository.findById(id);
    }

    @Override
    public Page<TaskEntity> findByUserId(String auth0Id, Pageable pageable) {
        return taskRepository.findByUser_Auth0Id(auth0Id, pageable);
    }

    @Override
    public Page<TaskEntity> findByUserIdAndTitle(String auth0Id, String title, Pageable pageable) {
        return taskRepository.findByUser_Auth0IdAndTitleContainingIgnoreCase(auth0Id, title, pageable);
    }

    @Override
    public Page<TaskEntity> findByUserIdAndCompleted(String auth0Id, Boolean completed, Pageable pageable) {
        return taskRepository.findByUser_Auth0IdAndCompleted(auth0Id, completed, pageable);
    }

    @Override
    public Page<TaskEntity> findByUserIdAndCompletedAndTitle(String auth0Id, Boolean completed, String title, Pageable pageable) {
        return taskRepository.findByUser_Auth0IdAndCompletedAndTitleContainingIgnoreCase(auth0Id, completed, title, pageable);
    }

    @Override
    public TaskEntity save(TaskEntity task) {
        return taskRepository.save(task);
    }

    @Override
    public void delete(TaskEntity task) {
        taskRepository.delete(task);
    }

    @Override
    public Optional<Integer> findMaxPriorityByUser(String auth0Id) {
        return taskRepository.findMaxPriorityByUser_Auth0Id(auth0Id);
    }

    @Override
    public boolean existsTaskWithPriority(String auth0Id, Integer priority) {
        return taskRepository.existsTaskWithPriority(auth0Id, priority);
    }

    @Override
    public void reducePrioritiesAfterCompletion(String auth0Id, Integer removedPriority) {
        taskRepository.reducePrioritiesAfterCompletion(auth0Id, removedPriority);
    }

    @Override
    public void incrementPriorities(String auth0Id, int from, int to) {
        taskRepository.incrementPriorities(auth0Id, from, to);
    }

    @Override
    public void decrementPriorities(String auth0Id, int from, int to) {
        taskRepository.decrementPriorities(auth0Id, from, to);
    }

}
