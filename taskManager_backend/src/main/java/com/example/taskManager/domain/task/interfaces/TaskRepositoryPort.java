package com.example.taskManager.domain.task.interfaces;

import com.example.taskManager.domain.task.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface TaskRepositoryPort {

    Task save(Task task);

    void delete(Task task);

    Optional<Task> findById(Integer id);

    Page<Task> findByUserId(Integer userId, Pageable pageable);

    Page<Task> findByUserIdAndTitle(Integer userId, String title, Pageable pageable);

    Page<Task> findByUserIdAndCompleted(Integer userId, Boolean completed, Pageable pageable);

    Page<Task> findByUserIdAndCompletedAndTitle(Integer userId, Boolean completed, String title, Pageable pageable);

    Optional<Integer> findMaxPriorityByUser(Integer userId);

    boolean existsTaskWithPriority(Integer userId, Integer priority);

    void reducePrioritiesAfterCompletion(Integer userId, Integer removedPriority);

    void incrementPriorities(Integer userId, int from, int to);

    void decrementPriorities(Integer userId, int from, int to);

}