package com.example.taskManager.domain.user.interfaces;

import com.example.taskManager.infrastructure.user.entities.User;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByAuth0Id(String auth0Id);
    User save(User user);
    
}