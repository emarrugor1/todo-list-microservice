package co.edu.emarrugo.todo_list_microservice.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("tasks")
public class TaskEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private boolean completed;
    private LocalDateTime deadline;

    @Column("todo_list_id")
    private String todoListId;
}