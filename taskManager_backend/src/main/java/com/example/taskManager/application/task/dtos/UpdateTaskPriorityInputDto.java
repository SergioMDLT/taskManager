package com.example.taskManager.application.task.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskPriorityInputDto {
    private Integer id;
    private Integer userId;
    private Integer priority;
    
}