package co.edu.emarrugo.todo_list_microservice.handler;

import co.edu.emarrugo.todo_list_microservice.model.entity.TaskEntity;
import co.edu.emarrugo.todo_list_microservice.model.entity.TodoListEntity;
import co.edu.emarrugo.todo_list_microservice.service.TodoListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.EntityResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HandlerTest {

    @Mock
    private TodoListService todoListService;

    @InjectMocks
    private Handler handler;

    private TodoListEntity todoListEntity;
    private TaskEntity taskEntity;
    private ServerRequest mockRequest;

    @BeforeEach
    void setUp() {
        todoListEntity = TodoListEntity.builder()
                .id(1)
                .name("Lista de tareas")
                .build();

        taskEntity = TaskEntity.builder()
                .id(1)
                .name("Tarea 1")
                .description("Descripción de la tarea 1")
                .completed(false)
                .deadline(LocalDateTime.now().plusDays(1))
                .todoListId(1)
                .build();

        mockRequest = MockServerRequest.builder()
                .pathVariable("id", "1")
                .build();
    }

    @Test
    void itShouldGetAllTodoLists() {
        // GIVEN
        List<TodoListEntity> todoLists = Arrays.asList(todoListEntity);
        when(todoListService.getAllTodoLists()).thenReturn(Flux.fromIterable(todoLists));

        // WHEN
        Mono<ServerResponse> response = handler.getAllTodoLists(mockRequest);

        // THEN
        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().is2xxSuccessful() &&
                                serverResponse instanceof EntityResponse)
                .verifyComplete();
        verify(todoListService, times(1)).getAllTodoLists();
    }

    @Test
    void itShouldGetTodoListById() {
        // GIVEN
        when(todoListService.getTodoListById(1)).thenReturn(Mono.just(todoListEntity));

        // WHEN
        Mono<ServerResponse> response = handler.getTodoListById(mockRequest);

        // THEN
        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().is2xxSuccessful() &&
                                serverResponse instanceof EntityResponse)
                .verifyComplete();
        verify(todoListService, times(1)).getTodoListById(1);
    }

    @Test
    void itShouldReturnNotFoundWhenTodoListNotExists() {
        // GIVEN
        when(todoListService.getTodoListById(1)).thenReturn(Mono.empty());

        // WHEN
        Mono<ServerResponse> response = handler.getTodoListById(mockRequest);

        // THEN
        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().value() == 404)
                .verifyComplete();
        verify(todoListService, times(1)).getTodoListById(1);
    }

    @Test
    void itShouldCreateTodoList() {
        // GIVEN
        MockServerRequest requestWithBody = MockServerRequest.builder()
                .body(Mono.just(todoListEntity));
        when(todoListService.createTodoList(any(TodoListEntity.class)))
                .thenReturn(Mono.just(todoListEntity));

        // WHEN
        Mono<ServerResponse> response = handler.createTodoList(requestWithBody);

        // THEN
        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().is2xxSuccessful() &&
                                serverResponse instanceof EntityResponse)
                .verifyComplete();
        verify(todoListService, times(1)).createTodoList(any(TodoListEntity.class));
    }

    @Test
    void itShouldUpdateTodoList() {
        // GIVEN
        MockServerRequest requestWithBody = MockServerRequest.builder()
                .pathVariable("id", "1")
                .body(Mono.just(todoListEntity));
        when(todoListService.updateTodoList(anyInt(), any(TodoListEntity.class)))
                .thenReturn(Mono.just(todoListEntity));

        // WHEN
        Mono<ServerResponse> response = handler.updateTodoList(requestWithBody);

        // THEN
        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().is2xxSuccessful() &&
                                serverResponse instanceof EntityResponse)
                .verifyComplete();
        verify(todoListService, times(1)).updateTodoList(anyInt(), any(TodoListEntity.class));
    }

    @Test
    void itShouldDeleteTodoList() {
        // GIVEN
        when(todoListService.deleteTodoList(1)).thenReturn(Mono.empty());

        // WHEN
        Mono<ServerResponse> response = handler.deleteTodoList(mockRequest);

        // THEN
        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
        verify(todoListService, times(1)).deleteTodoList(1);
    }

    @Test
    void itShouldGetTasksByTodoListId() {
        // GIVEN
        List<TaskEntity> tasks = Arrays.asList(taskEntity);
        when(todoListService.getTasksByTodoListId(1)).thenReturn(Flux.fromIterable(tasks));

        // WHEN
        Mono<ServerResponse> response = handler.getTasksByTodoListId(mockRequest);

        // THEN
        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().is2xxSuccessful() &&
                                serverResponse instanceof EntityResponse)
                .verifyComplete();
        verify(todoListService, times(1)).getTasksByTodoListId(1);
    }

    @Test
    void itShouldGetTaskById() {
        // GIVEN
        when(todoListService.getTaskById(1)).thenReturn(Mono.just(taskEntity));

        // WHEN
        Mono<ServerResponse> response = handler.getTaskById(mockRequest);

        // THEN
        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().is2xxSuccessful() &&
                                serverResponse instanceof EntityResponse)
                .verifyComplete();
        verify(todoListService, times(1)).getTaskById(1);
    }

    @Test
    void itShouldCreateTask() {
        // GIVEN
        MockServerRequest requestWithBody = MockServerRequest.builder()
                .body(Mono.just(taskEntity));
        when(todoListService.createTask(any(TaskEntity.class)))
                .thenReturn(Mono.just(taskEntity));

        // WHEN
        Mono<ServerResponse> response = handler.createTask(requestWithBody);

        // THEN
        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().is2xxSuccessful() &&
                                serverResponse instanceof EntityResponse)
                .verifyComplete();
        verify(todoListService, times(1)).createTask(any(TaskEntity.class));
    }

    @Test
    void itShouldUpdateTask() {
        // GIVEN
        MockServerRequest requestWithBody = MockServerRequest.builder()
                .pathVariable("id", "1")
                .body(Mono.just(taskEntity));
        when(todoListService.updateTask(anyInt(), any(TaskEntity.class)))
                .thenReturn(Mono.just(taskEntity));

        // WHEN
        Mono<ServerResponse> response = handler.updateTask(requestWithBody);

        // THEN
        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().is2xxSuccessful() &&
                                serverResponse instanceof EntityResponse)
                .verifyComplete();
        verify(todoListService, times(1)).updateTask(anyInt(), any(TaskEntity.class));
    }

    @Test
    void itShouldDeleteTask() {
        // GIVEN
        when(todoListService.deleteTask(1)).thenReturn(Mono.empty());

        // WHEN
        Mono<ServerResponse> response = handler.deleteTask(mockRequest);

        // THEN
        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
        verify(todoListService, times(1)).deleteTask(1);
    }

    @Test
    void itShouldGetTodoListWithTasks() {
        // GIVEN
        List<TaskEntity> tasks = Arrays.asList(taskEntity);
        TodoListEntity todoListWithTasks = TodoListEntity.builder()
                .id(1)
                .name("Lista de tareas")
                .tasks(tasks)
                .build();

        when(todoListService.getTodoListWithTasks(1)).thenReturn(Mono.just(todoListWithTasks));

        // WHEN
        Mono<ServerResponse> response = handler.getTodoListWithTasks(mockRequest);

        // THEN
        StepVerifier.create(response)
                .expectNextMatches(serverResponse ->
                        serverResponse.statusCode().is2xxSuccessful() &&
                                serverResponse instanceof EntityResponse)
                .verifyComplete();
        verify(todoListService, times(1)).getTodoListWithTasks(1);
    }
}