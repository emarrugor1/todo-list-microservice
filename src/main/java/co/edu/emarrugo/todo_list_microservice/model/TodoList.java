package co.edu.emarrugo.todo_list_microservice.model;

import lombok.Data;

import java.util.List;

@Data
public class TodoList {
    private List<Task> tasks;
}
