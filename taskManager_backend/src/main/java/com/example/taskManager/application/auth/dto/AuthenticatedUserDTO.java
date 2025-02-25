package com.example.taskManager.application.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticatedUserDTO {

    private final String    auth0Id;
    private final Integer   userId;
    private final String    role;
    
}
