package com.example.taskManager.application.task.mappers;

import org.springframework.stereotype.Component;
import com.example.taskManager.application.task.dtos.SearchTaskOutputDto;
import com.example.taskManager.domain.task.models.Task;

@Component
public class SearchTaskApplicationMapper {

    public SearchTaskOutputDto toDto(Task task) {
        SearchTaskOutputDto dto = new SearchTaskOutputDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setPriority(task.getPriority());
        dto.setCompleted(task.getCompleted());
        return dto;
    }

}