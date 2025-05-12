package com.example.taskManager.infrastructure.user.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.taskManager.application.user.dtos.CreateUserInputDTO;
import com.example.taskManager.application.user.dtos.CreateUserOutputDTO;
import com.example.taskManager.application.user.usecase.UserCreator;
import com.example.taskManager.infrastructure.auth.dtos.AuthenticatedUserDTO;
import com.example.taskManager.infrastructure.auth.services.AuthenticatedUserProvider;
import com.example.taskManager.infrastructure.user.dtos.PostUserRequestDTO;
import com.example.taskManager.infrastructure.user.dtos.PostUserResponseDTO;
import com.example.taskManager.infrastructure.user.mappers.PostUserDtoMapper;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:4200")
public class UserPostController {

    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final UserCreator               userCreator;
    private final PostUserDtoMapper         postUserDtoMapper;

    public UserPostController(
        AuthenticatedUserProvider   authenticatedUserProvider,
        UserCreator                 userCreator,
        PostUserDtoMapper           postUserDtoMapper
    ) {
        this.authenticatedUserProvider =    authenticatedUserProvider;
        this.userCreator =                  userCreator;
        this.postUserDtoMapper =            postUserDtoMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<PostUserResponseDTO> registerUser(@RequestBody PostUserRequestDTO requestDTO) {
        AuthenticatedUserDTO user = authenticatedUserProvider.execute();

        CreateUserInputDTO input = postUserDtoMapper.toInput(requestDTO, user.getUserId());
        CreateUserOutputDTO output = userCreator.execute(input);
        
        return ResponseEntity.ok(postUserDtoMapper.toResponse(output));
    }

}