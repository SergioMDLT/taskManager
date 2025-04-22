package com.example.taskManager.infrastructure.user.dtos;

import lombok.Data;

@Data
public class PostUserResponseDTO {
    private Integer id;
    private String email;
    private String role;

}