package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.example.constants.TaskStatus;
import org.example.model.TaskModel;
import org.example.persist.TaskRepository;
import org.example.persist.entity.TaskEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskModel add(String title, String description, LocalDate dueDate) {
        var e = TaskEntity.builder()
                .title(title)
                .description(description)
                .dueDate(Date.valueOf(dueDate))
                .status(TaskStatus.TODO)
                .build();

        var saved = this.taskRepository.save(e);
        return entityToObject(saved);
    }

    public List<TaskModel> getAll() {
        return this.taskRepository.findAll().stream()
                .map(this::entityToObject)
                .collect(Collectors.toList());
    }

    public List<TaskModel> getByDueDate(String dueDate) {
        return this.taskRepository.findAllByDueDate(Date.valueOf(dueDate)).stream()
                .map(this::entityToObject)
                .collect(Collectors.toList());
    }

    public List<TaskModel> getByStatus(TaskStatus status) {
        return this.taskRepository.findAllByStatus(status).stream()
                .map(this::entityToObject)
                .collect(Collectors.toList());
    }

    public TaskModel getOne(Long id) {
        var entity = this.getById(id);
        return this.entityToObject(entity);
    }

    public TaskModel update(Long id, String title, String description, LocalDate dueDate) {
        var exists = this.getById(id);

        exists.setTitle(Strings.isEmpty(title) ?
                exists.getTitle() : title);
        exists.setDescription(Strings.isEmpty(description) ?
                exists.getDescription() : description);

        exists.setDueDate(Objects.isNull(dueDate) ?
                exists.getDueDate() : Date.valueOf(dueDate));

        var updated = this.taskRepository.save(exists);
        return this.entityToObject(updated);
    }

    public TaskModel updateStatus(Long id, TaskStatus status) {
        var exist = this.getById(id);
        exist.setStatus(status);

        var statusUpdated = this.taskRepository.save(exist);
        return this.entityToObject(statusUpdated);
    }


        public boolean delete(Long id) {
        try{
            this.taskRepository.deleteById(id);
        }
        catch (Exception e){
            log.error("an error occurred while deleting [{}]", e.toString());
            return false;
        }

        return true;
    }

    //id 없을 경우 예외 처리 메서드 반환값이 달라서 따로 지정
    private TaskEntity getById(Long id) {
        return this.taskRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("not exists task id [%d]", id)));
    }


    private TaskModel entityToObject(TaskEntity e) {
        return TaskModel.builder()
                .id(e.getId())
                .title(e.getTitle())
                .description(e.getDescription())
                .status(e.getStatus())
                .dueDate(e.getDueDate().toString())
                .createdAt(e.getCreatedAt().toLocalDateTime())
                .updatedAt(e.getUpdatedAt().toLocalDateTime())
                .build();
    }



}
