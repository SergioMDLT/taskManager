package com.example.taskManager.application.user.mappers;

import org.springframework.stereotype.Component;
import com.example.taskManager.application.user.dtos.CreateUserInputDTO;
import com.example.taskManager.application.user.dtos.CreateUserOutputDTO;
import com.example.taskManager.domain.user.models.User;

@Component
public class CreateUserApplicationMapper {

    public User toDomain(CreateUserInputDTO inputDTO) {
        User user = new User();
        user.setEmail(inputDTO.getEmail());
        user.setRole(inputDTO.getRole());
        return user;
    }

    public CreateUserOutputDTO toOutput(User user) {
        CreateUserOutputDTO output = new CreateUserOutputDTO();
        output.setId(user.getId());
        output.setEmail(user.getEmail());
        output.setRole(user.getRole());
        return output;
    }

}