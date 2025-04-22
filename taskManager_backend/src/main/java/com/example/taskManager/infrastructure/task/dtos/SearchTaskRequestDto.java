package com.example.taskManager.infrastructure.task.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchTaskRequestDto {
    private Boolean completed;
    private Integer page;
    private Integer size;
    private String  sort;

}