package com.example.taskManager.infrastructure.user.adapters;

import com.example.taskManager.domain.user.interfaces.UserRepositoryPort;
import com.example.taskManager.domain.user.models.User;
import com.example.taskManager.infrastructure.user.entities.UserEntity;
import com.example.taskManager.infrastructure.user.mappers.UserEntityMapper;
import com.example.taskManager.infrastructure.user.repositories.UserRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserEntityMapper  userEntityMapper;
    private final UserRepository    userRepository;

    public UserRepositoryAdapter(
        UserEntityMapper    userEntityMapper,
        UserRepository      userRepository
    ) {
        this.userEntityMapper = userEntityMapper;
        this.userRepository =   userRepository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = userEntityMapper.toEntity(user);
        return userEntityMapper.toDomain(userRepository.save(entity));
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id)
            .map(userEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findByExternalId(String auth0Id) {
        return userRepository.findByAuth0Id(auth0Id)
            .map(userEntityMapper::toDomain);
    }

}