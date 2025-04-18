package co.edu.emarrugo.todo_list_microservice.handler;

import co.edu.emarrugo.todo_list_microservice.model.entity.TaskEntity;
import co.edu.emarrugo.todo_list_microservice.model.entity.TodoListEntity;
import co.edu.emarrugo.todo_list_microservice.service.TodoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class Handler {
    private final TodoListService todoListService;

    // Métodos para TodoList
    public Mono<ServerResponse> getAllTodoLists(ServerRequest request) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoListService.getAllTodoLists(), TodoListEntity.class);
    }

    public Mono<ServerResponse> getTodoListById(ServerRequest request) {
        Integer id = Integer.parseInt(request.pathVariable("id"));
        return todoListService.getTodoListById(id)
                .flatMap(todoList -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(todoList))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> createTodoList(ServerRequest request) {
        return request.bodyToMono(TodoListEntity.class)
                .flatMap(todoListService::createTodoList)
                .flatMap(savedTodoList -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(savedTodoList));
    }

    public Mono<ServerResponse> updateTodoList(ServerRequest request) {
        Integer id = Integer.parseInt(request.pathVariable("id"));
        return request.bodyToMono(TodoListEntity.class)
                .flatMap(todoList -> todoListService.updateTodoList(id, todoList))
                .flatMap(updatedTodoList -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(updatedTodoList))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> deleteTodoList(ServerRequest request) {
        Integer id = Integer.parseInt(request.pathVariable("id"));
        return todoListService.deleteTodoList(id)
                .then(ok().build())
                .switchIfEmpty(notFound().build());
    }

    // Métodos para Task
    public Mono<ServerResponse> getTasksByTodoListId(ServerRequest request) {
        Integer todoListId = Integer.parseInt(request.pathVariable("id"));
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(todoListService.getTasksByTodoListId(todoListId), TaskEntity.class);
    }

    public Mono<ServerResponse> getTaskById(ServerRequest request) {
        Integer id = Integer.parseInt(request.pathVariable("id"));
        return todoListService.getTaskById(id)
                .flatMap(task -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(task))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> createTask(ServerRequest request) {
        return request.bodyToMono(TaskEntity.class)
                .flatMap(todoListService::createTask)
                .flatMap(savedTask -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(savedTask));
    }

    public Mono<ServerResponse> updateTask(ServerRequest request) {
        Integer id = Integer.parseInt(request.pathVariable("id"));
        return request.bodyToMono(TaskEntity.class)
                .flatMap(task -> todoListService.updateTask(id, task))
                .flatMap(updatedTask -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(updatedTask))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> deleteTask(ServerRequest request) {
        Integer id = Integer.parseInt(request.pathVariable("id"));
        return todoListService.deleteTask(id)
                .then(ok().build())
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> getTodoListWithTasks(ServerRequest request) {
        Integer id = Integer.parseInt(request.pathVariable("id"));
        return todoListService.getTodoListWithTasks(id)
                .flatMap(todoListWithTasks -> ok().contentType(MediaType.APPLICATION_JSON).bodyValue(todoListWithTasks))
                .switchIfEmpty(notFound().build());
    }

}