package com.example.taskManager.task.application.query;

import org.springframework.data.domain.Page;
import com.example.taskManager.task.application.dto.TaskResponseDTO;

public interface ITaskQueryService {
    Page<TaskResponseDTO> getTasks(
        Integer id,
        Integer userId,
        String title,
        Boolean completed,
        int page,
        int size,
        String sort
    );
}
