package com.example.taskManager.domain.user.interfaces;

import java.util.Optional;
import com.example.taskManager.domain.user.models.User;

public interface UserRepositoryPort {

    User save(User user);

    Optional<User> findById(Integer id);

    Optional<User> findByExternalId(String externalId);

}