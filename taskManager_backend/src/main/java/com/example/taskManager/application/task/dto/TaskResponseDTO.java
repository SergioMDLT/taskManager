package com.example.taskManager.application.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {

    private Integer id;
    private String  auth0Id;
    private String  title;
    private String  description;
    private Boolean completed;
    private Integer priority;

}
