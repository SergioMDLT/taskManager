package com.example.taskManager.application.task.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.taskManager.application.user.auth.usecase.AuthenticatedUserProvider;
import com.example.taskManager.domain.task.interfaces.TaskRepositoryPort;
import com.example.taskManager.infrastructure.task.dtos.TaskRequestDTO;
import com.example.taskManager.infrastructure.task.dtos.TaskResponseDTO;
import com.example.taskManager.infrastructure.task.entities.TaskEntity;
import com.example.taskManager.infrastructure.task.mappers.TaskMapper;
import com.example.taskManager.infrastructure.user.entities.User;
import com.example.taskManager.infrastructure.user.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TaskCreator {

    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final TaskMapper                taskMapper;
    private final TaskRepositoryPort        taskRepositoryPort;
    private final UserRepository            userRepository;

    public TaskCreator(
            AuthenticatedUserProvider   authenticatedUserProvider,
            TaskMapper                  taskMapper,
            TaskRepositoryPort          taskRepositoryPort,
            UserRepository              userRepository
        ) {
            this.authenticatedUserProvider =    authenticatedUserProvider;
            this.taskMapper =                   taskMapper;
            this.taskRepositoryPort =           taskRepositoryPort;
            this.userRepository =               userRepository;
    }

    @Transactional
    public TaskResponseDTO execute(TaskRequestDTO taskRequestDTO) {
        String auth0Id = authenticatedUserProvider.execute().getAuth0Id();

        User user = userRepository.findByAuth0Id(auth0Id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with auth0Id: " + auth0Id));

        TaskEntity task = taskMapper.toEntity(taskRequestDTO);
        task.setUser(user);

        Integer assignedPriority = task.getPriority();

        if (assignedPriority == null) {
            assignedPriority = taskRepositoryPort.findMaxPriorityByUser(auth0Id).orElse(0) + 1;
        } else {
            if (taskRepositoryPort.existsTaskWithPriority(auth0Id, assignedPriority)) {
                throw new IllegalStateException("User already has a task with priority: " + assignedPriority);
            }
        }

        task.setPriority(assignedPriority);
        return taskMapper.toDTO(taskRepositoryPort.save(task));
    }

}