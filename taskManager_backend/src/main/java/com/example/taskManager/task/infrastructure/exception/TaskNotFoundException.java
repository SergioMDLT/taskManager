package com.example.taskManager.task.infrastructure.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException( String message ) {
        super( message );
    }
}
