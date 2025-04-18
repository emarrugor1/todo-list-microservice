package co.edu.emarrugo.todo_list_microservice.service;

import co.edu.emarrugo.todo_list_microservice.model.entity.TaskEntity;
import co.edu.emarrugo.todo_list_microservice.model.entity.TodoListEntity;
import co.edu.emarrugo.todo_list_microservice.model.repository.TaskRepository;
import co.edu.emarrugo.todo_list_microservice.model.repository.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoListService {
    private final TodoListRepository todoListRepository;
    private final TaskRepository taskRepository;

    public Flux<TodoListEntity> getAllTodoLists() {
        return todoListRepository.findAll();
    }

    public Mono<TodoListEntity> getTodoListById(Integer id) {
        return todoListRepository.findById(id);
    }

    public Mono<TodoListEntity> createTodoList(TodoListEntity todoList) {
        return todoListRepository.save(todoList);
    }

    public Mono<TodoListEntity> updateTodoList(Integer id, TodoListEntity todoList) {
        return todoListRepository.findById(id)
                .flatMap(existingTodoList -> {
                    existingTodoList.setName(todoList.getName());
                    return todoListRepository.save(existingTodoList);
                });
    }

    public Mono<Void> deleteTodoList(Integer id) {
        return taskRepository.findAllByTodoListId(id)
                .flatMap(taskRepository::delete)
                .then(todoListRepository.deleteById(id));
    }

    public Flux<TaskEntity> getTasksByTodoListId(Integer todoListId) {
        return taskRepository.findAllByTodoListId(todoListId);
    }

    public Mono<TaskEntity> getTaskById(Integer id) {
        return taskRepository.findById(id);
    }

    public Mono<TaskEntity> createTask(TaskEntity task) {
        return taskRepository.save(task);
    }

    public Mono<TaskEntity> updateTask(Integer id, TaskEntity task) {
        return taskRepository.findById(id)
                .flatMap(existingTask -> {
                    existingTask.setName(task.getName());
                    existingTask.setDescription(task.getDescription());
                    existingTask.setCompleted(task.isCompleted());
                    existingTask.setDeadline(task.getDeadline());
                    return taskRepository.save(existingTask);
                });
    }

    public Mono<Void> deleteTask(Integer id) {
        return taskRepository.deleteById(id);
    }

    public Mono<TodoListEntity> getTodoListWithTasks(Integer id) {
        return todoListRepository.findById(id)
                .flatMap(todoListEntity -> taskRepository.findAllByTodoListId(id)
                        .collectList()
                        .map(tasks -> {
                            todoListEntity.setTasks(tasks);
                            return todoListEntity;
                        }));
    }

}