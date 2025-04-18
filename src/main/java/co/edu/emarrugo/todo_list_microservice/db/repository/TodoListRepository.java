package co.edu.emarrugo.todo_list_microservice.db.repository;

import co.edu.emarrugo.todo_list_microservice.db.entity.TodoListEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends R2dbcRepository<TodoListEntity, String> {
}