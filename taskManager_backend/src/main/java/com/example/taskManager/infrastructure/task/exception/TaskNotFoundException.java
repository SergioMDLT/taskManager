package com.example.taskManager.infrastructure.task.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException( String message ) {
        super( message );
    }
}
