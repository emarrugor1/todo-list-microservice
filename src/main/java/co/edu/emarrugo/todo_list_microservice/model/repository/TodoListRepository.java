package co.edu.emarrugo.todo_list_microservice.model.repository;

import co.edu.emarrugo.todo_list_microservice.model.entity.TodoListEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends ReactiveCrudRepository<TodoListEntity, Integer> {
}