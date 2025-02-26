package com.example.taskManager.application.task;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.example.taskManager.application.task.dto.TaskRequestDTO;
import com.example.taskManager.application.task.dto.TaskResponseDTO;
import com.example.taskManager.application.task.usecase.CreateTaskUseCase;
import com.example.taskManager.application.task.usecase.DeleteTaskUseCase;
import com.example.taskManager.application.task.usecase.GetTaskByIdUseCase;
import com.example.taskManager.application.task.usecase.GetTasksByUserAndCompletionStatusUseCase;
import com.example.taskManager.application.task.usecase.GetTasksByUserAndTitleUseCase;
import com.example.taskManager.application.task.usecase.GetTasksByUserAndTitleAndCompletionStatusUseCase;
import com.example.taskManager.application.task.usecase.GetTasksByUserUseCase;
import com.example.taskManager.application.task.usecase.UpdateTaskPriorityUseCase;
import com.example.taskManager.application.task.usecase.UpdateTaskStatusUseCase;

@Component
public class TaskUseCaseFacade {

    private final CreateTaskUseCase                                 createTaskUseCase;
    private final DeleteTaskUseCase                                 deleteTaskUseCase;
    private final GetTaskByIdUseCase                                getTaskByIdUseCase;
    private final GetTasksByUserAndCompletionStatusUseCase          getTasksByCompletionStatusUseCase;
    private final GetTasksByUserAndTitleUseCase                     getTasksByTitleUseCase;
    private final GetTasksByUserAndTitleAndCompletionStatusUseCase  getTasksByTitleAndCompletionUseCase;
    private final GetTasksByUserUseCase                             getTasksByUserUseCase;
    private final UpdateTaskPriorityUseCase                         updateTaskPriorityUseCase;
    private final UpdateTaskStatusUseCase                           updateTaskStatusUseCase;

    public TaskUseCaseFacade(
        CreateTaskUseCase                                   createTaskUseCase,
        DeleteTaskUseCase                                   deleteTaskUseCase,
        GetTaskByIdUseCase                                  getTaskByIdUseCase,
        GetTasksByUserAndCompletionStatusUseCase            getTasksByCompletionStatusUseCase,
        GetTasksByUserAndTitleUseCase                       getTasksByTitleUseCase,
        GetTasksByUserAndTitleAndCompletionStatusUseCase    getTasksByTitleAndCompletionUseCase,
        GetTasksByUserUseCase                               getTasksByUserUseCase,
        UpdateTaskPriorityUseCase                           updateTaskPriorityUseCase,
        UpdateTaskStatusUseCase                             updateTaskStatusUseCase
    ) {
        this.createTaskUseCase =                    createTaskUseCase;
        this.deleteTaskUseCase =                    deleteTaskUseCase;
        this.getTaskByIdUseCase =                   getTaskByIdUseCase;
        this.getTasksByCompletionStatusUseCase =    getTasksByCompletionStatusUseCase;
        this.getTasksByTitleUseCase =               getTasksByTitleUseCase;
        this.getTasksByTitleAndCompletionUseCase =  getTasksByTitleAndCompletionUseCase;
        this.getTasksByUserUseCase =                getTasksByUserUseCase;
        this.updateTaskPriorityUseCase =            updateTaskPriorityUseCase;
        this.updateTaskStatusUseCase =              updateTaskStatusUseCase;
    }

    public TaskResponseDTO getTaskById( Integer id ) {
        return getTaskByIdUseCase.execute( id );
    }

    public Page<TaskResponseDTO> getTasksByUser( String auth0Id, int page, int size, String sort ) {
        return getTasksByUserUseCase.execute( auth0Id, page, size, sort );
    }

    public Page<TaskResponseDTO> getTasksByTitle( String auth0Id, String title, int page, int size ) {
        return getTasksByTitleUseCase.execute( auth0Id, title, page, size );
    }

    public Page<TaskResponseDTO> getTasksByCompletionStatus( String auth0Id, Boolean completed, int page, int size ) {
        return getTasksByCompletionStatusUseCase.execute( auth0Id, completed, page, size );
    }

    public Page<TaskResponseDTO> getTasksByTitleAndCompletion( String auth0Id, Boolean completed, String title, int page, int size ) {
        return getTasksByTitleAndCompletionUseCase.execute( auth0Id, completed, title, page, size );
    }

    public TaskResponseDTO createTask( TaskRequestDTO taskRequestDTO ) {
        return createTaskUseCase.execute( taskRequestDTO );
    }

    public void updateTaskPriority( Integer id, Integer priority ) {
        updateTaskPriorityUseCase.execute( id, priority );
    }

    public TaskResponseDTO updateTaskStatus( Integer id ) {
        return updateTaskStatusUseCase.execute( id );
    }

    public void deleteTask( Integer id, String auth0Id ) {
        deleteTaskUseCase.execute( id, auth0Id );
    }
    
}
