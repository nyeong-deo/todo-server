package org.example.persist.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.constants.TaskStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@Entity(name = "TASK")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    private Date dueDate;

    @CreationTimestamp
    @Column(insertable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(insertable = false, updatable = false)
    private Timestamp updatedAt;
}
