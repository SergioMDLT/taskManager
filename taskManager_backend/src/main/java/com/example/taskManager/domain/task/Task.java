package com.example.taskManager.domain.task;

import com.example.taskManager.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(
    name = "tasks",
    uniqueConstraints = @UniqueConstraint( columnNames = { "user_id", "priority" })
)
public class Task {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    Integer id;

    @Column( name = "title" )
    String title;

    @Column( name = "description" )
    String description;

    @Column( name = "completed" )
    Boolean completed;

    @Column( name = "priority", nullable = true )
    Integer priority = 0;

    @ManyToOne
    @JoinColumn( name = "user_id", nullable = false )
    private User user;
    
}
