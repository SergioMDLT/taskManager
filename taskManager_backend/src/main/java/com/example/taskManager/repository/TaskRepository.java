package com.example.taskManager.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taskManager.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
    Optional<Task> findByTitle( String title );
    List<Task> findByCompletedTrue();
    List<Task> findByCompletedFalse();
    void deleteByTitle( String title );
}
