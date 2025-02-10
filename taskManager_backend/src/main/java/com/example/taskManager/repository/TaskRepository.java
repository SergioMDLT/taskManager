package com.example.taskManager.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.taskManager.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{
    List<Task> findByTitleContainingIgnoreCase( String title );
    List<Task> findByCompleted( Boolean completed );
}
