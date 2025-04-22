package com.example.taskManager.application.user.dtos;

import lombok.Data;

@Data
public class CreateUserInputDTO {
    private Integer id;
    private String email;
    private String role;
    
}