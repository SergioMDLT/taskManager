package com.example.taskManager.task.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {
    private Integer  userId;
    private String   title;
    private String   description;
    private Boolean  completed;
    private Integer  priority;
}
