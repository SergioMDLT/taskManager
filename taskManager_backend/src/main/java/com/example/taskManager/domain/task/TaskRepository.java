package com.example.taskManager.domain.task;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{

    Page<Task> findByUser_Auth0Id( String auth0Id, Pageable pageable );

    Page<Task> findByUser_Auth0IdAndCompleted( String auth0Id, Boolean completed, Pageable pageable );

    Page<Task> findByUser_Auth0IdAndTitleContainingIgnoreCase( String auth0Id, String title, Pageable pageable );

    Page<Task> findByUser_Auth0IdAndCompletedAndTitleContainingIgnoreCase( String auth0Id, Boolean completed, String title, Pageable pageable );

    @Query( "SELECT COUNT(t) > 0 FROM Task t WHERE t.user.auth0Id = :auth0Id AND t.priority = :priority" )
    boolean existsTaskWithPriority( @Param( "auth0Id" ) String auth0Id, @Param( "priority" ) Integer priority );

    @Query( "SELECT MAX(t.priority) FROM Task t WHERE t.user.auth0Id = :auth0Id" )
    Optional<Integer> findMaxPriorityByUser_Auth0Id( @Param( "auth0Id" ) String auth0Id );

    @Modifying
    @Transactional
    @Query( value = """
        UPDATE tasks t
        JOIN users u ON u.id = t.user_id
        SET t.priority = t.priority + 1
        WHERE u.auth0id = :auth0Id
          AND t.priority >= :newPriority
          AND t.id != :taskId
        """, nativeQuery = true)
    void shiftPrioritiesUp(
        @Param( "auth0Id" ) String auth0Id,
        @Param( "taskId" ) Integer taskId,
        @Param( "newPriority" ) Integer newPriority
    );

    @Modifying
    @Transactional
    @Query( value = """
        UPDATE tasks t
        JOIN users u ON u.id = t.user_id
        SET t.priority = t.priority - 1
        WHERE u.auth0id = :auth0Id
          AND t.priority <= :newPriority
          AND t.id != :taskId
        """, nativeQuery = true)
    void shiftPrioritiesDown(
        @Param( "auth0Id" ) String auth0Id,
        @Param( "taskId" ) Integer taskId,
        @Param( "newPriority" ) Integer newPriority
    );

    @Modifying
    @Transactional
    @Query( value = """
        UPDATE tasks t
        JOIN users u ON u.id = t.user_id
        SET t.priority = t.priority - 1
        WHERE u.auth0id = :auth0Id
          AND t.priority > :removedPriority
        """, nativeQuery = true)
    void reducePrioritiesAfterCompletion(
        @Param( "auth0Id" ) String auth0Id,
        @Param( "removedPriority" ) Integer removedPriority
    );

}
