package com.example.taskManager.application.task.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTaskCompletionOutputDto {
    private Integer id;
    private String title;
    private String description;
    private Boolean completed;
    private Integer priority;

}