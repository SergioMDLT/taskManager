package com.example.taskManager.infrastructure.task.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatchTaskPriorityResponseDto {
    private Integer id;
    private String  title;
    private String  description;
    private Boolean completed;
    private Integer priority;

}