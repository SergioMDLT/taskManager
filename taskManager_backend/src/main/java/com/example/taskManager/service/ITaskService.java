package com.example.taskManager.service;

import java.util.List;

import com.example.taskManager.model.Task;

public interface ITaskService {
    public List<Task> getAllTasks();
    public Task getTaskById( Integer id );
    public List<Task> getTaskByTitle( String title );
    public List<Task> getByCompleted( Boolean completed );
    public Task createTask( Task task );
    public Task updateTaskStatus ( Integer id );
    public void deleteTaskById( Integer id );
}
