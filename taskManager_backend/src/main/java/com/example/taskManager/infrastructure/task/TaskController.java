package com.example.taskManager.infrastructure.task;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.taskManager.application.auth.AuthService;
import com.example.taskManager.application.task.TaskUseCaseFacade;
import com.example.taskManager.application.task.dto.TaskRequestDTO;
import com.example.taskManager.application.task.dto.TaskResponseDTO;
import com.example.taskManager.application.task.dto.TaskUpdateRequestDTO;


@RestController
@RequestMapping( "/tasks" )
@CrossOrigin( value = "http://localhost:4200" )
public class TaskController {
    
    private final AuthService authService;
    private final TaskUseCaseFacade taskUseCaseFacade;

    public TaskController(AuthService authService, TaskUseCaseFacade taskUseCaseFacade) {
        this.authService = authService;
        this.taskUseCaseFacade = taskUseCaseFacade;
    }

    @GetMapping( "/{id}" )
    public ResponseEntity<TaskResponseDTO> getTaskById( @PathVariable Integer id ) {
        return ResponseEntity.ok( taskUseCaseFacade.getTaskById( id ));
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> getTasks(
        @AuthenticationPrincipal Jwt jwt,
        @RequestParam( required = false ) Integer id,
        @RequestParam( required = false ) String title,
        @RequestParam( required = false ) Boolean completed,
        @RequestParam( defaultValue = "0") int page,
        @RequestParam( defaultValue = "10") int size,
        @RequestParam( defaultValue = "id") String sort
    ) {
        String auth0Id = jwt.getClaim( "sub" );

        if ( auth0Id == null ) {
            throw new IllegalArgumentException( "El auth0Id no se encontró en el token" );
        }
        
        if ( title != null && completed != null ) {
            return ResponseEntity.ok( taskUseCaseFacade.getTasksByTitleAndCompletion( auth0Id, completed, title, page, size ));
        } else if ( completed != null ) {
            return ResponseEntity.ok( taskUseCaseFacade.getTasksByCompletionStatus( auth0Id, completed, page, size ));
        } else if ( title != null ) {
            return ResponseEntity.ok( taskUseCaseFacade.getTasksByTitle( auth0Id, title, page, size ));
        } else {
            return ResponseEntity.ok( taskUseCaseFacade.getTasksByUser( auth0Id, page, size, "id" ));
        }

        //TO DO: que los admins puedan ver todas las tareas de la BD
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
        @AuthenticationPrincipal Jwt jwt,
        @RequestBody TaskRequestDTO taskRequestDTO
    ) {
        String auth0Id = jwt.getClaim( "sub" );
        if ( auth0Id == null ) {
            throw new IllegalArgumentException( "El auth0Id no se encontró en el token" );
        }

        taskRequestDTO.setAuth0Id( auth0Id );
        TaskResponseDTO createdTask = taskUseCaseFacade.createTask( taskRequestDTO );
        return ResponseEntity.ok( createdTask );
    }

    @PatchMapping( "/{id}" )
    public ResponseEntity<TaskResponseDTO> updateTask(
        @PathVariable Integer id,
        @RequestBody TaskUpdateRequestDTO request
    ) {
        if ( request == null || ( request.getCompleted() == null && request.getPriority() == null )) {
           
            return ResponseEntity.ok( taskUseCaseFacade.updateTaskStatus( id ));
        }
    
        TaskResponseDTO updatedTask = null;
    
        if ( request.getCompleted() != null ) {
            updatedTask = taskUseCaseFacade.updateTaskStatus( id );
        }
    
        if ( request.getPriority() != null ) {
            taskUseCaseFacade.updateTaskPriority( id, request.getPriority() );
            updatedTask = taskUseCaseFacade.getTaskById( id );
        }

        return ResponseEntity.ok( updatedTask );
    }

    @DeleteMapping( "/{id}" )
    public ResponseEntity<Void> deleteTask( @PathVariable Integer id ) {
        String auth0Id = authService.getAuthenticatedUser().getAuth0Id(); 
        taskUseCaseFacade.deleteTask( id, auth0Id );
        return ResponseEntity.noContent().build();
    }

}
