package com.example.taskManager.application.task.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taskManager.application.auth.AuthService;
import com.example.taskManager.application.task.dto.TaskRequestDTO;
import com.example.taskManager.application.task.dto.TaskResponseDTO;
import com.example.taskManager.domain.task.Task;
import com.example.taskManager.domain.task.TaskRepository;
import com.example.taskManager.domain.user.User;
import com.example.taskManager.domain.user.UserRepository;
import com.example.taskManager.infrastructure.task.adapter.TaskMapper;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CreateTaskUseCase {

    private final TaskRepository taskRepository;
    private final TaskMapper     taskMapper;
    private final AuthService    authService;
    private final UserRepository userRepository;

    public CreateTaskUseCase(
        TaskRepository taskRepository,
        TaskMapper     taskMapper,
        AuthService    authService,
        UserRepository userRepository
        ) {
        this.taskRepository = taskRepository;
        this.taskMapper =     taskMapper;
        this.authService =    authService;
        this.userRepository = userRepository;
    }

    @Transactional
    public TaskResponseDTO execute( TaskRequestDTO taskRequestDTO ) {
        String auth0Id = authService.getAuthenticatedUser().getAuth0Id();

        User user = userRepository.findByAuth0Id( auth0Id )
                .orElseThrow(() -> new EntityNotFoundException( "User not found with auth0Id: " + auth0Id ));

        Task task = taskMapper.toEntity( taskRequestDTO );
        task.setUser( user );

        if ( task.getPriority() == null ) {
            Integer maxPriority = taskRepository.findMaxPriorityByUser_Auth0Id( auth0Id ).orElse( 0 );
            task.setPriority( maxPriority + 1 );
        }

        return taskMapper.toDTO( taskRepository.save( task ));
    }
    
}
