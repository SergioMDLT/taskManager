package com.example.taskManager.service;

import java.util.List;

import com.example.taskManager.model.Task;

public interface ITaskService {
    public List<Task> getAllTasks();
    public Task getTaskById( Integer id );
    public Task getTaskByTitle( String title );
    public List<Task> getCompletedTasks();
    public Task createTask( Task task );
    public Task updateTask ( Integer id, Task task);
    public void deleteTaskById( Integer id );
    public void deleteTaskByTitle( String title );
}
