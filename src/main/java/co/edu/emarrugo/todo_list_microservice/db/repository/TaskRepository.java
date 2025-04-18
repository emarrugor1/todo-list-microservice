package co.edu.emarrugo.todo_list_microservice.db.repository;


import co.edu.emarrugo.todo_list_microservice.db.entity.TaskEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TaskRepository extends R2dbcRepository<TaskEntity, String> {
    Flux<TaskEntity> findAllByTodoListId(String todoListId);
}