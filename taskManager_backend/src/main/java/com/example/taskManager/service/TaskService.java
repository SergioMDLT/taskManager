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
    public Task getTaskById(Integer id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public Task getTaskByTitle(String title) {
        return taskRepository.findByTitle(title).orElse(null);
    }

    @Override
    public List<Task> getCompletedTasks() {
        return taskRepository.findByCompletedTrue();
    }

    @Override
    public Task createTask(Task task) {
       return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Integer id, Task taskDetails) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setCompleted(taskDetails.getCompleted());
            return taskRepository.save(task);
        }
        return null;
    }

    @Override
    public void deleteTaskById(Integer id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void deleteTaskByTitle(String title) {
        taskRepository.deleteByTitle( title );
    }
}
