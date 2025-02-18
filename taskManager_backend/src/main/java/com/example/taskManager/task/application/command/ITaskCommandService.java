package com.example.taskManager.task.application.command;

import com.example.taskManager.task.application.dto.TaskRequestDTO;
import com.example.taskManager.task.application.dto.TaskResponseDTO;

public interface ITaskCommandService {

    TaskResponseDTO createTask( TaskRequestDTO taskRequestDTO );

    TaskResponseDTO updateTaskStatus( Integer id );

    void updatePriority( Integer id, Integer newPriority );

    void deleteTaskById( Integer id );
    
}
