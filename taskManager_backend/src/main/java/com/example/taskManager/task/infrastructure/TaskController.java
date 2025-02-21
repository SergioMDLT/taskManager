package com.example.taskManager.task.infrastructure;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.taskManager.auth.application.AuthService;
import com.example.taskManager.task.application.command.ITaskCommandService;
import com.example.taskManager.task.application.dto.TaskRequestDTO;
import com.example.taskManager.task.application.dto.TaskResponseDTO;
import com.example.taskManager.task.application.query.ITaskQueryService;

@RestController
@RequestMapping( "/tasks" )
@CrossOrigin( value = "http://localhost:4200" )
public class TaskController {
    
    private final ITaskQueryService taskQueryService;
    private final ITaskCommandService taskCommandService;
    private final AuthService authService;

    public TaskController( ITaskQueryService taskQueryService, ITaskCommandService taskCommandService, AuthService authService ){
        this.taskQueryService = taskQueryService;
        this.taskCommandService = taskCommandService;
        this.authService = authService;
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
        Integer userId = jwt.getClaim("sub");
        if (userId == null) {
            throw new IllegalArgumentException("El userId no se encontró en el token.");
        }
        
        Page<TaskResponseDTO> tasks = taskQueryService.getTasks( id, userId, title, completed, page, size, sort );
        return ResponseEntity.ok( tasks );

        //TO DO: que los admins puedan ver todas las tareas de la BD
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask( @RequestBody TaskRequestDTO taskRequestDTO ){
        Integer userId = authService.getAuthenticatedUser().getUserId();
        taskRequestDTO.setUserId( userId );
        TaskResponseDTO createdTask = taskCommandService.createTask( taskRequestDTO );
        return ResponseEntity.ok( createdTask );
    }

    @PutMapping( "/{id}" )
    public ResponseEntity<TaskResponseDTO> updateTask( @PathVariable Integer id ){
        TaskResponseDTO updatedTask = taskCommandService.updateTaskStatus( id );
        return ResponseEntity.ok( updatedTask );
    }

    @PatchMapping( "/{id}/priority" )
    public ResponseEntity<Void> updateTaskPriority(
        @PathVariable Integer id,
        @RequestParam Integer newPriority
    ) {
        taskCommandService.updatePriority( id, newPriority );
        return ResponseEntity.ok().build();
    }


    @DeleteMapping( "/{id}" )
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        Integer userId = authService.getAuthenticatedUser().getUserId(); 
        taskCommandService.deleteTask(id, userId);  // ✅ Verifica que pasas `userId`
        return ResponseEntity.noContent().build();  // ✅ Devuelve 204 (sin contenido)
    }


}
