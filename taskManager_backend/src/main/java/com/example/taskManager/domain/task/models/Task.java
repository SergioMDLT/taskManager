package com.example.taskManager.domain.task.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Integer   id;
    private String    title;
    private String    description;
    private boolean   completed;
    private Integer   priority;
    private Integer   userId;
}
