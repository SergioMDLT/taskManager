package com.example.taskManager.task.application.command;

import java.util.Optional;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.example.taskManager.auth.application.AuthService;
import com.example.taskManager.task.application.dto.TaskRequestDTO;
import com.example.taskManager.task.application.dto.TaskResponseDTO;
import com.example.taskManager.task.domain.Task;
import com.example.taskManager.task.domain.TaskRepository;
import com.example.taskManager.task.infrastructure.adapter.TaskMapper;
import com.example.taskManager.task.infrastructure.exception.TaskNotFoundException;
import com.example.taskManager.user.domain.User;
import jakarta.transaction.Transactional;

@Service
public class TaskCommandService implements ITaskCommandService {
    private final TaskRepository taskRepository;
    private final TaskMapper     taskMapper;
    private final AuthService    authService;

    public TaskCommandService( TaskRepository taskRepository, TaskMapper taskMapper, AuthService authService ) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.authService = authService;
    }

    @Override
    public TaskResponseDTO createTask( TaskRequestDTO taskRequestDTO ) {
        Integer userId = authService.getAuthenticatedUser().getUserId();
        Task task = taskMapper.toEntity( taskRequestDTO );
        User user = new User();
        user.setId( userId );
        task.setUser( user );
        if ( task.getPriority() == null ) {
            Integer maxPriority = taskRepository.findMaxPriorityByUserId( userId ).orElse( 0 );
            task.setPriority(maxPriority + 1);
        }
        return taskMapper.toDTO( taskRepository.save( task ));
    }

    @Override
    public TaskResponseDTO updateTaskStatus( Integer id ) {
        Task task = taskRepository.findById( id )
            .orElseThrow(() -> new TaskNotFoundException( "Task not found with ID: " + id ));

        Integer userId = task.getUser().getId();
        task.setCompleted( !task.getCompleted() );

        if ( !task.getCompleted().booleanValue() ) {
            task.setPriority( null );
        } else {
            Integer maxPriority = taskRepository.findMaxPriorityByUserId( userId ).orElse( 0 );
            task.setPriority( maxPriority + 1 );
        }

        return taskMapper.toDTO( taskRepository.save( task ));
    }

    @Override
    @Transactional
    public synchronized void updatePriority(Integer id, Integer newPriority) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));
    
        Integer userId = task.getUser().getId();
        Integer oldPriority = task.getPriority();
    
        if (newPriority.equals(oldPriority)) {
            return; // No hay cambios que hacer
        }
    
        System.out.println("🔄 Cambiando prioridad de tarea ID " + id + " de " + oldPriority + " a " + newPriority);
    
        if (newPriority > oldPriority) {
            // Baja las prioridades de las tareas intermedias
            System.out.println("⬇️ Reduciendo prioridad de tareas en el rango: " + oldPriority + " → " + newPriority);
            taskRepository.updatePrioritiesDown(userId, oldPriority, newPriority);
        } else {
            // Sube las prioridades de las tareas intermedias
            System.out.println("⬆️ Aumentando prioridad de tareas en el rango: " + newPriority + " → " + oldPriority);
            taskRepository.updatePrioritiesUp(userId, newPriority, oldPriority);
        }
    
        // Asignamos la nueva prioridad a la tarea y la guardamos
        task.setPriority(newPriority);
        taskRepository.save(task);
        
        System.out.println("✅ Prioridad actualizada correctamente para tarea ID " + id);
    }





    @Override
    public void deleteTask(Integer taskId, Integer userId) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException("Tarea no encontrada"));
    
        if (!task.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("No puedes eliminar esta tarea");
        }
    
        taskRepository.delete(task);
    }

}
