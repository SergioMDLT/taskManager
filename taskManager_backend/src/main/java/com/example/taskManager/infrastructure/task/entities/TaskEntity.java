package com.example.taskManager.infrastructure.task.entities;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import com.example.taskManager.infrastructure.user.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "tasks")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "completed")
    Boolean completed;

    @Column(name = "priority", nullable = true)
    Integer priority = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private UserEntity user;

}