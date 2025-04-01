package com.example.taskManager.infrastructure.user.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.taskManager.infrastructure.user.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByAuth0Id( String auth0Id );
    
}