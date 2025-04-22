package com.example.taskManager.application.user.usecase;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.taskManager.application.user.dtos.CreateUserInputDTO;
import com.example.taskManager.application.user.dtos.CreateUserOutputDTO;
import com.example.taskManager.application.user.mappers.CreateUserApplicationMapper;
import com.example.taskManager.domain.user.interfaces.UserRepositoryPort;
import com.example.taskManager.domain.user.models.User;

@Service
public class UserCreator {

    private final CreateUserApplicationMapper userApplicationMapper;
    private final UserRepositoryPort          userRepositoryPort;

    public UserCreator(
        CreateUserApplicationMapper userApplicationMapper,
        UserRepositoryPort          userRepositoryPort
    ) {
        this.userApplicationMapper =    userApplicationMapper;
        this.userRepositoryPort =       userRepositoryPort;
    }

    public CreateUserOutputDTO execute(CreateUserInputDTO inputDTO) {
        Optional<User> existingUser = userRepositoryPort.findById(inputDTO.getId());
    
        if (existingUser.isPresent()) {
            return userApplicationMapper.toOutput(existingUser.get());
        }
    
        User newUser = userApplicationMapper.toDomain(inputDTO);
        User savedUser = userRepositoryPort.save(newUser);
        return userApplicationMapper.toOutput(savedUser);
    }    

}