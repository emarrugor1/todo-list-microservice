package co.edu.emarrugo.todo_list_microservice.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("todo_lists")
public class TodoListEntity {
    @Id
    private String id;
    private String name;
}