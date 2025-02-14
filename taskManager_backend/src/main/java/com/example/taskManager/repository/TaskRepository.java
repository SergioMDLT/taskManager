package com.example.taskManager.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.taskManager.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>{

    Page<Task> findByTitleContainingIgnoreCase( String title, Pageable pageable );

    Page<Task> findByCompletedAndTitleContainingIgnoreCase( Boolean completed, String title, Pageable pageable );

    Page<Task> findByCompleted( Boolean completed, Pageable pageable );

    @Query( "SELECT MAX(t.priority) FROM Task t" )
    Optional<Integer> findMaxPriority();

    @Modifying
    @Query( "UPDATE Task t SET t.priority = t.priority - 1 WHERE t.priority >= :start AND t.priority <= :end" )
    void updatePrioritiesDown(@Param( "start" ) Integer start, @Param( "end" ) Integer end );

    @Modifying
    @Query( "UPDATE Task t SET t.priority = t.priority + 1 WHERE t.priority >= :start AND t.priority <= :end" )
    void updatePrioritiesUp(@Param( "start" ) Integer start, @Param( "end" ) Integer end );

}
