package org.example.web.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.constants.TaskStatus;

@Getter
@Setter
@ToString
public class TaskStatusRequest {
    private TaskStatus status;
}
