package com.example.taskManager.infrastructure.user.dtos;

import lombok.Data;

@Data
public class PostUserRequestDTO {
    private String email;
    private String role;
    
}