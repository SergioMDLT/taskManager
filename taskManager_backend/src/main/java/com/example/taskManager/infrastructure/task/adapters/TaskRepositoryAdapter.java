package com.example.taskManager.infrastructure.task.adapters;

import com.example.taskManager.domain.task.interfaces.TaskRepositoryPort;
import com.example.taskManager.domain.task.models.Task;
import com.example.taskManager.infrastructure.task.entities.TaskEntity;
import com.example.taskManager.infrastructure.task.mappers.TaskEntityMapper;
import com.example.taskManager.infrastructure.task.repositories.TaskRepository;
import com.example.taskManager.infrastructure.user.entities.UserEntity;
import com.example.taskManager.infrastructure.user.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final TaskRepository    taskRepository;
    private final TaskEntityMapper  taskEntityMapper;
    private final UserRepository    userRepository;

    public TaskRepositoryAdapter(
        TaskRepository      taskRepository,
        TaskEntityMapper    taskEntityMapper,
        UserRepository      userRepository
    ) {
        this.taskRepository =   taskRepository;
        this.taskEntityMapper = taskEntityMapper;
        this.userRepository =   userRepository;
    }

    private String resolveAuth0Id(Integer userId) {
        return userRepository.findById(userId)
            .map(UserEntity::getAuth0Id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    @Override
    public Task save(Task task) {
        TaskEntity entity = taskEntityMapper.toEntity(task);
        TaskEntity savedEntity = taskRepository.save(entity);
        return taskEntityMapper.toDomain(savedEntity);
    }

    @Override
    public void delete(Task task) {
        TaskEntity entity = taskEntityMapper.toEntity(task);
        taskRepository.delete(entity);
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return taskRepository.findById(id).map(taskEntityMapper::toDomain);
    }

    @Override
    public Page<Task> findByUserId(Integer userId, Pageable pageable) {
        return taskRepository.findByUser_Auth0Id(resolveAuth0Id(userId), pageable)
            .map(taskEntityMapper::toDomain);
    }

    @Override
    public Page<Task> findByUserIdAndTitle(Integer userId, String title, Pageable pageable) {
        return taskRepository.findByUser_Auth0IdAndTitleContainingIgnoreCase(resolveAuth0Id(userId), title, pageable)
            .map(taskEntityMapper::toDomain);
    }

    @Override
    public Page<Task> findByUserIdAndCompleted(Integer userId, Boolean completed, Pageable pageable) {
        return taskRepository.findByUser_Auth0IdAndCompleted(resolveAuth0Id(userId), completed, pageable)
            .map(taskEntityMapper::toDomain);
    }

    @Override
    public Page<Task> findByUserIdAndCompletedAndTitle(Integer userId, Boolean completed, String title, Pageable pageable) {
        return taskRepository.findByUser_Auth0IdAndCompletedAndTitleContainingIgnoreCase(resolveAuth0Id(userId), completed, title, pageable)
            .map(taskEntityMapper::toDomain);
    }

    @Override
    public Optional<Integer> findMaxPriorityByUser(Integer userId) {
        return taskRepository.findMaxPriorityByUser_Auth0Id(resolveAuth0Id(userId));
    }

    @Override
    public boolean existsTaskWithPriority(Integer userId, Integer priority) {
        return taskRepository.existsTaskWithPriority(resolveAuth0Id(userId), priority);
    }

    @Override
    public void reducePrioritiesAfterCompletion(Integer userId, Integer removedPriority) {
        taskRepository.reducePrioritiesAfterCompletion(resolveAuth0Id(userId), removedPriority);
    }

    @Override
    public void incrementPriorities(Integer userId, int from, int to) {
        taskRepository.incrementPriorities(resolveAuth0Id(userId), from, to);
    }

    @Override
    public void decrementPriorities(Integer userId, int from, int to) {
        taskRepository.decrementPriorities(resolveAuth0Id(userId), from, to);
    }

}