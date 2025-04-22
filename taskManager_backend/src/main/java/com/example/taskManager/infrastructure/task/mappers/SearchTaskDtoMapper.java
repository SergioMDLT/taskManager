package com.example.taskManager.infrastructure.task.mappers;

import org.springframework.stereotype.Component;
import com.example.taskManager.application.task.dtos.SearchTaskInputDto;
import com.example.taskManager.application.task.dtos.SearchTaskOutputDto;
import com.example.taskManager.infrastructure.task.dtos.SearchTaskRequestDto;
import com.example.taskManager.infrastructure.task.dtos.SearchTaskResponseDto;

@Component
public class SearchTaskDtoMapper {

    public SearchTaskInputDto toInput(SearchTaskRequestDto dto, Integer userId) {
        SearchTaskInputDto input = new SearchTaskInputDto();
        input.setCompleted(dto.getCompleted());
        input.setPage(dto.getPage());
        input.setSize(dto.getSize());
        input.setSort(dto.getSort());
        input.setUserId(userId);
        return input;
    }

    public SearchTaskResponseDto toResponse(SearchTaskOutputDto output) {
        SearchTaskResponseDto dto = new SearchTaskResponseDto();
        dto.setId(output.getId());
        dto.setTitle(output.getTitle());
        dto.setDescription(output.getDescription());
        dto.setCompleted(output.getCompleted());
        dto.setPriority(output.getPriority());
        return dto;
    }

}