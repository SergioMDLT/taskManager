package com.example.taskManager.user.application.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String auth0Id;
    private String email;
    private String role;
}
