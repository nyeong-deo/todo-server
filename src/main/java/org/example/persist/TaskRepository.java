package org.example.persist;

import org.example.constants.TaskStatus;
import org.example.persist.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Long> {
    List<TaskEntity> findAllByDueDate(Date duedate);
    List<TaskEntity> findAllByStatus(TaskStatus status);
}
