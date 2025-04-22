package com.example.taskManager.application.task.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskSearchFiltersDTO {
    private Integer userId;
    private String  title;
    private Boolean completed;
    private int     page;
    private int     size;
    private String  sort;

}