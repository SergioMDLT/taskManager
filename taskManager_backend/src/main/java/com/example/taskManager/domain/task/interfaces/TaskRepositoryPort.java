package com.example.taskManager.domain.task.interfaces;

import com.example.taskManager.infrastructure.task.entities.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface TaskRepositoryPort {

    Optional<TaskEntity> findById(Integer id);

    Page<TaskEntity> findByUserId(String auth0Id, Pageable pageable);

    Page<TaskEntity> findByUserIdAndTitle(String auth0Id, String title, Pageable pageable);

    Page<TaskEntity> findByUserIdAndCompleted(String auth0Id, Boolean completed, Pageable pageable);

    Page<TaskEntity> findByUserIdAndCompletedAndTitle(String auth0Id, Boolean completed, String title, Pageable pageable);

    TaskEntity save(TaskEntity task);

    void delete(TaskEntity task);

    Optional<Integer> findMaxPriorityByUser(String auth0Id);

    boolean existsTaskWithPriority(String auth0Id, Integer priority);

    void reducePrioritiesAfterCompletion(String auth0Id, Integer removedPriority);

    void incrementPriorities(String auth0Id, int from, int to);

    void decrementPriorities(String auth0Id, int from, int to);

}
