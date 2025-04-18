package co.edu.emarrugo.todo_list_microservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoListEntity {
    @Id
    private Integer id;
    private String name;

    @Transient
    private List<TaskEntity> tasks;
}