package com.example.taskManager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.taskManager.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
    Page<Task> findByTitleContainingIgnoreCase( String title, Pageable pageable );
    Page<Task> findByCompletedAndTitleContainingIgnoreCase(Boolean completed, String title, Pageable pageable);
    Page<Task> findByCompleted( Boolean completed, Pageable pageable );
}
