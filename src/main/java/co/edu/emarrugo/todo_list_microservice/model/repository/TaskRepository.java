package co.edu.emarrugo.todo_list_microservice.model.repository;


import co.edu.emarrugo.todo_list_microservice.model.entity.TaskEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TaskRepository extends ReactiveCrudRepository<TaskEntity, Integer> {
    Flux<TaskEntity> findAllByTodoListId(Integer todoListId);
}