package com.example.taskManager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.taskManager.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{
    Optional<Task> findByTitle( String title );
    List<Task> findByCompletedTrue();
    void deleteByTitle( String title );
}
