package co.edu.emarrugo.todo_list_microservice.controller;

import co.edu.emarrugo.todo_list_microservice.handler.Handler;
import co.edu.emarrugo.todo_list_microservice.model.entity.TaskEntity;
import co.edu.emarrugo.todo_list_microservice.model.entity.TodoListEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoListControllerTest {

    @Mock
    private Handler handler;

    @InjectMocks
    private TodoListController todoListController;

    private WebTestClient webTestClient;
    private TodoListEntity todoListEntity;
    private TaskEntity taskEntity;

    @BeforeEach
    void setUp() {
        // Inicializar el cliente web con las rutas de nuestro controlador
        RouterFunction<ServerResponse> routerFunction = todoListController.todoListRoutes();
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();

        // Crear una entidad TodoList para las pruebas
        todoListEntity = TodoListEntity.builder()
                .id(1)
                .name("Lista de tareas")
                .build();

        // Crear una entidad Task para las pruebas
        taskEntity = TaskEntity.builder()
                .id(1)
                .name("Tarea 1")
                .description("Descripción de la tarea")
                .completed(false)
                .deadline(LocalDateTime.now().plusDays(1))
                .todoListId(1)
                .build();
    }

    @Test
    @DisplayName("TC-01: Verificar endpoint para obtener todas las listas de tareas")
    void itShouldGetAllTodoLists() {
        // GIVEN
        List<TodoListEntity> todoLists = Arrays.asList(todoListEntity);
        Mono<ServerResponse> responseBuilder = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(todoLists);

        when(handler.getAllTodoLists(any())).thenReturn(responseBuilder);

        // WHEN / THEN
        webTestClient.get()
                .uri("/api/todolists")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(TodoListEntity.class)
                .hasSize(1)
                .contains(todoListEntity);

        verify(handler, times(1)).getAllTodoLists(any());
    }

    @Test
    @DisplayName("TC-02: Verificar endpoint para obtener una lista de tareas por ID")
    void itShouldGetTodoListById() {
        // GIVEN
        Mono<ServerResponse> responseBuilder = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(todoListEntity);

        when(handler.getTodoListById(any())).thenReturn(responseBuilder);

        // WHEN / THEN
        webTestClient.get()
                .uri("/api/todolists/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(TodoListEntity.class)
                .isEqualTo(todoListEntity);

        verify(handler, times(1)).getTodoListById(any());
    }

    @Test
    @DisplayName("TC-03: Verificar endpoint para crear una lista de tareas")
    void itShouldCreateTodoList() {
        // GIVEN
        Mono<ServerResponse> responseBuilder = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(todoListEntity);

        when(handler.createTodoList(any())).thenReturn(responseBuilder);

        // WHEN / THEN
        webTestClient.post()
                .uri("/api/todolists")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(todoListEntity)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(TodoListEntity.class)
                .isEqualTo(todoListEntity);

        verify(handler, times(1)).createTodoList(any());
    }

    @Test
    @DisplayName("TC-04: Verificar endpoint para actualizar una lista de tareas")
    void itShouldUpdateTodoList() {
        // GIVEN
        Mono<ServerResponse> responseBuilder = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(todoListEntity);

        when(handler.updateTodoList(any())).thenReturn(responseBuilder);

        // WHEN / THEN
        webTestClient.put()
                .uri("/api/todolists/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(todoListEntity)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(TodoListEntity.class)
                .isEqualTo(todoListEntity);

        verify(handler, times(1)).updateTodoList(any());
    }

    @Test
    @DisplayName("TC-05: Verificar endpoint para eliminar una lista de tareas")
    void itShouldDeleteTodoList() {
        // GIVEN
        Mono<ServerResponse> responseBuilder = ServerResponse.ok().build();

        when(handler.deleteTodoList(any())).thenReturn(responseBuilder);

        // WHEN / THEN
        webTestClient.delete()
                .uri("/api/todolists/1")
                .exchange()
                .expectStatus().isOk();

        verify(handler, times(1)).deleteTodoList(any());
    }

    @Test
    @DisplayName("TC-06: Verificar endpoint para obtener tareas por ID de lista")
    void itShouldGetTasksByTodoListId() {
        // GIVEN
        List<TaskEntity> tasks = Arrays.asList(taskEntity);
        Mono<ServerResponse> responseBuilder = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(tasks);

        when(handler.getTasksByTodoListId(any())).thenReturn(responseBuilder);

        // WHEN / THEN
        webTestClient.get()
                .uri("/api/todolists/1/tasks")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(TaskEntity.class)
                .hasSize(1)
                .contains(taskEntity);

        verify(handler, times(1)).getTasksByTodoListId(any());
    }

    @Test
    @DisplayName("TC-07: Verificar endpoint para obtener lista con sus tareas")
    void itShouldGetTodoListWithTasks() {
        // GIVEN
        List<TaskEntity> tasks = Arrays.asList(taskEntity);
        TodoListEntity todoListWithTasks = TodoListEntity.builder()
                .id(1)
                .name("Lista de tareas")
                .tasks(tasks)
                .build();

        Mono<ServerResponse> responseBuilder = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(todoListWithTasks);

        when(handler.getTodoListWithTasks(any())).thenReturn(responseBuilder);

        // WHEN / THEN
        webTestClient.get()
                .uri("/api/todolists/1/withTasks")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(TodoListEntity.class)
                .isEqualTo(todoListWithTasks);

        verify(handler, times(1)).getTodoListWithTasks(any());
    }

    @Test
    @DisplayName("TC-08: Verificar endpoint para obtener una tarea por ID")
    void itShouldGetTaskById() {
        // GIVEN
        Mono<ServerResponse> responseBuilder = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(taskEntity);

        when(handler.getTaskById(any())).thenReturn(responseBuilder);

        // WHEN / THEN
        webTestClient.get()
                .uri("/api/tasks/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(TaskEntity.class)
                .isEqualTo(taskEntity);

        verify(handler, times(1)).getTaskById(any());
    }

    @Test
    @DisplayName("TC-09: Verificar endpoint para crear una tarea")
    void itShouldCreateTask() {
        // GIVEN
        Mono<ServerResponse> responseBuilder = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(taskEntity);

        when(handler.createTask(any())).thenReturn(responseBuilder);

        // WHEN / THEN
        webTestClient.post()
                .uri("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(taskEntity)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(TaskEntity.class)
                .isEqualTo(taskEntity);

        verify(handler, times(1)).createTask(any());
    }

    @Test
    @DisplayName("TC-10: Verificar endpoint para actualizar una tarea")
    void itShouldUpdateTask() {
        // GIVEN
        TaskEntity updatedTask = TaskEntity.builder()
                .id(1)
                .name("Tarea actualizada")
                .description("Nueva descripción")
                .completed(true)
                .deadline(LocalDateTime.now().plusDays(2))
                .todoListId(1)
                .build();

        Mono<ServerResponse> responseBuilder = ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedTask);

        when(handler.updateTask(any())).thenReturn(responseBuilder);

        // WHEN / THEN
        webTestClient.put()
                .uri("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedTask)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(TaskEntity.class)
                .isEqualTo(updatedTask);

        verify(handler, times(1)).updateTask(any());
    }

    @Test
    @DisplayName("TC-11: Verificar endpoint para eliminar una tarea")
    void itShouldDeleteTask() {
        // GIVEN
        Mono<ServerResponse> responseBuilder = ServerResponse.ok().build();

        when(handler.deleteTask(any())).thenReturn(responseBuilder);

        // WHEN / THEN
        webTestClient.delete()
                .uri("/api/tasks/1")
                .exchange()
                .expectStatus().isOk();

        verify(handler, times(1)).deleteTask(any());
    }
}