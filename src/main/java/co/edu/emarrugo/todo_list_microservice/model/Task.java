package co.edu.emarrugo.todo_list_microservice.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Task {
    private String id;
    private String name;
    private String description;
    private boolean completed;
    private LocalDateTime deadline;
}
