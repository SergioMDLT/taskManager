package com.example.taskManager.infrastructure.user.adapters;

import com.example.taskManager.domain.user.interfaces.UserRepositoryPort;
import com.example.taskManager.infrastructure.user.entities.User;
import com.example.taskManager.infrastructure.user.repositories.UserRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;

    public UserRepositoryAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByAuth0Id(String auth0Id) {
        return userRepository.findByAuth0Id(auth0Id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

}