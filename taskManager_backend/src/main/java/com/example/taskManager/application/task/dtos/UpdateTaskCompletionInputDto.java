package com.example.taskManager.application.task.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateTaskCompletionInputDto {
    private Integer id;
    private Integer userId;

}