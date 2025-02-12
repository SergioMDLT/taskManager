package com.example.taskManager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.taskManager.model.Task;

public interface ITaskService {
    public Page<Task> getAllTasks( Pageable pageable );
    public Task getTaskById( Integer id );
    public Page<Task> getTasksByTitle( String title, Pageable pageable );
    public Page<Task> getTasksByCompleted( Boolean completed, Pageable pageable );
    public Page<Task> getTasksByCompletedAndTitle( Boolean completed, String title, Pageable pageable );
    public Task createTask( Task task );
    public Task updateTaskStatus ( Integer id );
    public void deleteTaskById( Integer id );
    public void updatePriority( Integer id, Integer newPriority );
}
