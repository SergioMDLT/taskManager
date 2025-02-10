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
    public List<Task> getTasks(
        @RequestParam( required = false ) Integer id,
        @RequestParam( required = false ) String title,
        @RequestParam( required = false ) Boolean completed
    ) {
        if ( id != null ) {
            return List.of( taskService.getTaskById( id ) );
        }
        if ( title != null ) {
            return taskService.getTaskByTitle( title );
        }
        if ( completed != null ) {
            return completed ? taskService.getByCompleted( true ) : taskService.getByCompleted( false );
        }
        return taskService.getAllTasks();
    }

    @PostMapping
    public Task createTask( @RequestBody Task task ){
        return taskService.createTask( task );
    }

    @PutMapping("/{id}")
    public Task updateTask( @PathVariable Integer id ){
        return taskService.updateTaskStatus( id );
    }

    @DeleteMapping("/{id}")
    public void deleteTask( @PathVariable Integer id ) {
        taskService.deleteTaskById( id );
    }
}
