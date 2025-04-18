package co.edu.emarrugo.todo_list_microservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("todo_lists")
public class TodoListEntity {
    @Id
    private Integer id;
    private String name;

    @Transient
    private List<TaskEntity> tasks;
}