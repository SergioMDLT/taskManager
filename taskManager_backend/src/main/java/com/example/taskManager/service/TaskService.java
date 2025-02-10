package com.example.taskManager.service;

import com.example.taskManager.model.Task;
import com.example.taskManager.repository.TaskRepository;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TaskService implements ITaskService{

    private final TaskRepository taskRepository;
    public TaskService( TaskRepository taskRepository ) {
         this.taskRepository = taskRepository;
    }
    
    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById( Integer id ) {
        return taskRepository.findById( id ).orElse(null);
    }

    @Override
    public List<Task> getTaskByTitle( String title ) {
        return taskRepository.findByTitleContainingIgnoreCase( title );
    }

    @Override
    public List<Task> getByCompleted( Boolean completed ) {
        return taskRepository.findByCompleted( completed );
    }

    @Override
    public Task createTask( Task task ) {
       return taskRepository.save( task );
    }

    @Override
    public Task updateTaskStatus( Integer id ) {
        Task task = taskRepository.findById( id )
            .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));
        task.setCompleted(!task.getCompleted());
        return taskRepository.save( task );
    }

    @Override
    public void deleteTaskById( Integer id ) {
        taskRepository.deleteById( id );
    }
    
}
