package co.edu.emarrugo.todo_list_microservice.service;

import co.edu.emarrugo.todo_list_microservice.model.entity.TaskEntity;
import co.edu.emarrugo.todo_list_microservice.model.entity.TodoListEntity;
import co.edu.emarrugo.todo_list_microservice.model.repository.TaskRepository;
import co.edu.emarrugo.todo_list_microservice.model.repository.TodoListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
class TodoListServiceTest {

    @Mock
    private TodoListRepository todoListRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TodoListService todoListService;

    private TodoListEntity todoListEntity;
    private TaskEntity taskEntity;

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
    }

    @Test
    void itShouldGetAllTodoLists() {
        // GIVEN
        List<TodoListEntity> todoLists = Arrays.asList(todoListEntity);
        when(todoListRepository.findAll()).thenReturn(Flux.fromIterable(todoLists));

        // WHEN
        Flux<TodoListEntity> result = todoListService.getAllTodoLists();

        // THEN
        StepVerifier.create(result)
                .expectNext(todoListEntity)
                .verifyComplete();
        verify(todoListRepository, times(1)).findAll();
    }

    @Test
    void itShouldGetTodoListById() {
        // GIVEN
        when(todoListRepository.findById(1)).thenReturn(Mono.just(todoListEntity));

        // WHEN
        Mono<TodoListEntity> result = todoListService.getTodoListById(1);

        // THEN
        StepVerifier.create(result)
                .expectNext(todoListEntity)
                .verifyComplete();
        verify(todoListRepository, times(1)).findById(1);
    }

    @Test
    void itShouldCreateTodoList() {
        // GIVEN
        when(todoListRepository.save(any(TodoListEntity.class))).thenReturn(Mono.just(todoListEntity));

        // WHEN
        Mono<TodoListEntity> result = todoListService.createTodoList(todoListEntity);

        // THEN
        StepVerifier.create(result)
                .expectNext(todoListEntity)
                .verifyComplete();
        verify(todoListRepository, times(1)).save(todoListEntity);
    }

    @Test
    void itShouldUpdateTodoList() {
        // GIVEN
        TodoListEntity updatedTodoList = TodoListEntity.builder()
                .id(1)
                .name("Lista actualizada")
                .build();

        when(todoListRepository.findById(1)).thenReturn(Mono.just(todoListEntity));
        when(todoListRepository.save(any(TodoListEntity.class))).thenReturn(Mono.just(updatedTodoList));

        // WHEN
        Mono<TodoListEntity> result = todoListService.updateTodoList(1, updatedTodoList);

        // THEN
        StepVerifier.create(result)
                .expectNext(updatedTodoList)
                .verifyComplete();
        verify(todoListRepository, times(1)).findById(1);
        verify(todoListRepository, times(1)).save(any(TodoListEntity.class));
    }

    @Test
    void itShouldDeleteTodoList() {
        // GIVEN
        when(taskRepository.findAllByTodoListId(1)).thenReturn(Flux.just(taskEntity));
        when(taskRepository.delete(any(TaskEntity.class))).thenReturn(Mono.empty());
        when(todoListRepository.deleteById(1)).thenReturn(Mono.empty());

        // WHEN
        Mono<Void> result = todoListService.deleteTodoList(1);

        // THEN
        StepVerifier.create(result)
                .verifyComplete();
        verify(taskRepository, times(1)).findAllByTodoListId(1);
        verify(taskRepository, times(1)).delete(taskEntity);
        verify(todoListRepository, times(1)).deleteById(1);
    }

    @Test
    void itShouldGetTasksByTodoListId() {
        // GIVEN
        when(taskRepository.findAllByTodoListId(1)).thenReturn(Flux.just(taskEntity));

        // WHEN
        Flux<TaskEntity> result = todoListService.getTasksByTodoListId(1);

        // THEN
        StepVerifier.create(result)
                .expectNext(taskEntity)
                .verifyComplete();
        verify(taskRepository, times(1)).findAllByTodoListId(1);
    }

    @Test
    void itShouldGetTaskById() {
        // GIVEN
        when(taskRepository.findById(1)).thenReturn(Mono.just(taskEntity));

        // WHEN
        Mono<TaskEntity> result = todoListService.getTaskById(1);

        // THEN
        StepVerifier.create(result)
                .expectNext(taskEntity)
                .verifyComplete();
        verify(taskRepository, times(1)).findById(1);
    }

    @Test
    void itShouldCreateTask() {
        // GIVEN
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(Mono.just(taskEntity));

        // WHEN
        Mono<TaskEntity> result = todoListService.createTask(taskEntity);

        // THEN
        StepVerifier.create(result)
                .expectNext(taskEntity)
                .verifyComplete();
        verify(taskRepository, times(1)).save(taskEntity);
    }

    @Test
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

        when(taskRepository.findById(1)).thenReturn(Mono.just(taskEntity));
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(Mono.just(updatedTask));

        // WHEN
        Mono<TaskEntity> result = todoListService.updateTask(1, updatedTask);

        // THEN
        StepVerifier.create(result)
                .expectNext(updatedTask)
                .verifyComplete();
        verify(taskRepository, times(1)).findById(1);
        verify(taskRepository, times(1)).save(any(TaskEntity.class));
    }

    @Test
    void itShouldDeleteTask() {
        // GIVEN
        when(taskRepository.deleteById(1)).thenReturn(Mono.empty());

        // WHEN
        Mono<Void> result = todoListService.deleteTask(1);

        // THEN
        StepVerifier.create(result)
                .verifyComplete();
        verify(taskRepository, times(1)).deleteById(1);
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

        when(todoListRepository.findById(1)).thenReturn(Mono.just(todoListEntity));
        when(taskRepository.findAllByTodoListId(1)).thenReturn(Flux.fromIterable(tasks));

        // WHEN
        Mono<TodoListEntity> result = todoListService.getTodoListWithTasks(1);

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(todoList ->
                        todoList.getId().equals(1) &&
                                todoList.getTasks().size() == 1 &&
                                todoList.getTasks().get(0).getId().equals(1)
                )
                .verifyComplete();
        verify(todoListRepository, times(1)).findById(1);
        verify(taskRepository, times(1)).findAllByTodoListId(1);
    }
}