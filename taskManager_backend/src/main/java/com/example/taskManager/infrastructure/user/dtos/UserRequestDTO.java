package com.example.taskManager.infrastructure.user.dtos;

import lombok.Data;

@Data
public class UserRequestDTO {

    private String auth0Id;
    private String email;
    private String role;
    
}
