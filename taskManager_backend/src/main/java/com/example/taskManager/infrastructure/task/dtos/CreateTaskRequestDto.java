package com.example.taskManager.infrastructure.task.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTaskRequestDto {
    private String  title;
    private String  description;
    private Integer priority;
    
}