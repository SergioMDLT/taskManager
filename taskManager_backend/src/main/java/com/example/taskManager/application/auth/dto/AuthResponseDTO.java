package com.example.taskManager.application.auth.dto;

public class AuthResponseDTO {

    private String accessToken;

    public AuthResponseDTO( String accessToken ) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() { return accessToken; }
}

