package com.example.taskManager.task.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{

    Page<Task> findByUserId( Integer userId, Pageable pageable );

    Page<Task> findByUserIdAndCompleted( Integer userId, Boolean completed, Pageable pageable );

    Page<Task> findByUserIdAndTitleContainingIgnoreCase( Integer userId, String title, Pageable pageable );

    Page<Task> findByUserIdAndCompletedAndTitleContainingIgnoreCase( Integer userId, Boolean completed, String title, Pageable pageable );

    @Query( "SELECT MAX(t.priority) FROM Task t WHERE t.user.id = :userId" )
    Optional<Integer> findMaxPriorityByUserId( @Param( "userId" ) Integer userId );

    @Modifying
    @Query("UPDATE Task t SET t.priority = t.priority - 1 WHERE t.user.id = :userId AND t.priority BETWEEN :start AND :end" )
    void updatePrioritiesDown( @Param( "userId" ) Integer userId, @Param( "start" ) Integer start, @Param( "end" ) Integer end) ;

    @Modifying
    @Query("UPDATE Task t SET t.priority = t.priority + 1 WHERE t.user.id = :userId AND t.priority BETWEEN :start AND :end" )
    void updatePrioritiesUp( @Param( "userId" ) Integer userId, @Param( "start" ) Integer start, @Param( "end" ) Integer end );

}
