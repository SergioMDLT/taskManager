package com.example.taskManager.infrastructure.auth.dtos;

public class AuthResponseDTO {

    private String accessToken;

    public AuthResponseDTO( String accessToken ) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() { return accessToken; }
}