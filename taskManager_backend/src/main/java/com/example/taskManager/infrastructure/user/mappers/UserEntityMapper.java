package com.example.taskManager.infrastructure.user.mappers;

import com.example.taskManager.infrastructure.user.entities.UserEntity;
import org.springframework.stereotype.Component;
import com.example.taskManager.domain.user.models.User;

@Component
public class UserEntityMapper {

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;

        return new User(
            entity.getId(),
            entity.getEmail(),
            entity.getRole()
        );
    }

    public UserEntity toEntity(User user) {
        if (user == null) return null;

        return UserEntity.builder()
            .id(user.getId())
            .email(user.getEmail())
            .role(user.getRole())
            .build();
    }

}
