package com.example.taskManager.service.query;

import com.example.taskManager.dto.TaskResponseDTO;
import org.springframework.data.domain.Page;

public interface ITaskQueryService {
    Page<TaskResponseDTO> getTasks(
        Integer id,
        String title,
        Boolean completed,
        int page,
        int size,
        String sort
    );
}
