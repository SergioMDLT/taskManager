package com.example.taskManager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "task")
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
    @Column( name = "priority", unique = true, nullable = false )
    Integer priority = 0;
}
