package com.example.taskManager.service.command;

import com.example.taskManager.dto.TaskRequestDTO;
import com.example.taskManager.dto.TaskResponseDTO;

public interface ITaskCommandService {

    TaskResponseDTO createTask( TaskRequestDTO taskRequestDTO );

    TaskResponseDTO updateTaskStatus( Integer id );

    void updatePriority( Integer id, Integer newPriority );

    void deleteTaskById( Integer id );
    
}
