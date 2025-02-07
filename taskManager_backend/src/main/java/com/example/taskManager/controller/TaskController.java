package com.example.taskManager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskManager.model.Task;
import com.example.taskManager.service.TaskService;

@RestController
@RequestMapping( "/tasks" )
@CrossOrigin( value = "http://localhost:4200" )
public class TaskController {
    
    private TaskService taskService;
    public TaskController( TaskService taskService ){
        this.taskService = taskService;
    }
    
    @GetMapping("/tasks")
    public List<Task> getAllTasks(){
        return this.taskService.getAllTasks();
    }

    @GetMapping("/tasks/{id}")
    public Task getTaskById( @PathVariable Integer id ){
        return this.taskService.getTaskById( id );
    }

    @GetMapping("/tasks/by-title")
    public Task getTaskByTitle( @RequestParam String title ){
        return this.taskService.getTaskByTitle( title );
    }

    @GetMapping("/tasks/completed")
    public List<Task> getCompletedTasks(){
        return this.taskService.getCompletedTasks();
    }

    @GetMapping("/tasks/pending")
    public List<Task> getPendingTasks(){
        return this.taskService.getPendingTasks();
    }

    @PostMapping("/tasks")
    public Task createTask( @RequestBody Task task ){
        return taskService.createTask(task);
    }

    @PutMapping("/tasks/{id}")
    public Task updateTask( @PathVariable Integer id, @RequestBody Task task){
        return taskService.updateTask( id, task );
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTaskById( @PathVariable Integer id ){
        taskService.deleteTaskById( id );
    }

    @DeleteMapping("/tasks/by-title")
    public void deleteTaskByTitle( @RequestParam String title ){
        taskService.deleteTaskByTitle( title );
    }


}
