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
    
    @GetMapping
    public List<Task> getAllTasks(){
        return this.taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById( @PathVariable Integer id ){
        return this.taskService.getTaskById( id );
    }

    @GetMapping("/by-title")
    public Task getTaskByTitle( @RequestParam String title ){
        return this.taskService.getTaskByTitle( title );
    }

    @GetMapping("/completed")
    public List<Task> getCompletedTasks(){
        return this.taskService.getCompletedTasks();
    }

    @GetMapping("/pending")
    public List<Task> getPendingTasks(){
        return this.taskService.getPendingTasks();
    }

    @PostMapping
    public Task createTask( @RequestBody Task task ){
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask( @PathVariable Integer id, @RequestBody Task task){
        return taskService.updateTask( id, task );
    }

    @DeleteMapping("/{id}")
    public void deleteTaskById( @PathVariable Integer id ){
        taskService.deleteTaskById( id );
    }

    @DeleteMapping("/by-title")
    public void deleteTaskByTitle( @RequestParam String title ){
        taskService.deleteTaskByTitle( title );
    }


}
