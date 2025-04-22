package com.example.taskManager.infrastructure.user.mappers;

import org.springframework.stereotype.Component;
import com.example.taskManager.application.user.dtos.CreateUserInputDTO;
import com.example.taskManager.application.user.dtos.CreateUserOutputDTO;
import com.example.taskManager.infrastructure.user.dtos.PostUserRequestDTO;
import com.example.taskManager.infrastructure.user.dtos.PostUserResponseDTO;

@Component
public class PostUserDtoMapper {

    public CreateUserInputDTO toInput(PostUserRequestDTO requestDTO, Integer userId) {
        CreateUserInputDTO input = new CreateUserInputDTO();
        input.setId(userId);
        input.setEmail(requestDTO.getEmail());
        input.setRole(requestDTO.getRole());
        return input;
    }

    public PostUserResponseDTO toResponse(CreateUserOutputDTO outputDTO) {
        PostUserResponseDTO response = new PostUserResponseDTO();
        response.setId(outputDTO.getId());
        response.setEmail(outputDTO.getEmail());
        response.setRole(outputDTO.getRole());
        return response;
    }
    
}