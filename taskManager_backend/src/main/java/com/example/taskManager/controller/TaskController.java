package com.example.taskManager.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public Page<Task> getTasks(
        @RequestParam( required = false ) Integer id,
        @RequestParam( required = false ) String title,
        @RequestParam( required = false ) Boolean completed,
        @RequestParam( defaultValue = "0") int page,
        @RequestParam( defaultValue = "10") int size,
        @RequestParam( defaultValue = "id") String sort
    ) {
        List<String> validSortFields = List.of("id", "title", "description", "completed");
        String validatedSort = validSortFields.contains( sort ) ? sort : "id";
        Pageable pageable = PageRequest.of( page, size, Sort.by( validatedSort ));
        
        if ( id != null ) {
            Task task = taskService.getTaskById( id );
            if ( task == null ) {
                return Page.empty(pageable);
            }
            return new PageImpl<>( List.of( task ), pageable, 1);
        }
        if ( completed != null && title != null ) {
            return taskService.getTasksByCompletedAndTitle( completed, title, PageRequest.of( page, size, Sort.by(Sort.Direction.ASC, "priority" )));
        }
        if ( title != null ) {
            return taskService.getTasksByTitle( title, PageRequest.of( page, size, Sort.by(Sort.Direction.ASC, "priority" )));
        }
        if ( completed != null ) {
            return taskService.getTasksByCompleted( completed, PageRequest.of( page, size, Sort.by(Sort.Direction.ASC, "priority" )) );
        }
        return taskService.getAllTasks( PageRequest.of( page, size, Sort.by(Sort.Direction.ASC, "priority" )) );
    }

    @PostMapping
    public Task createTask( @RequestBody Task task ){
        return taskService.createTask( task );
    }

    @PutMapping( "/{id}" )
    public Task updateTask( @PathVariable Integer id ){
        return taskService.updateTaskStatus( id );
    }

    @PatchMapping( "/{id}/priority" )
    public ResponseEntity<Void> updateTaskPriority(
        @PathVariable Integer id,
        @RequestParam Integer newPriority
    ) {
        taskService.updatePriority( id, newPriority );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping( "/{id}" )
    public void deleteTask( @PathVariable Integer id ) {
        taskService.deleteTaskById( id );
    }
}
