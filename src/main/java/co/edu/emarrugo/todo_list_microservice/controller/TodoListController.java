package co.edu.emarrugo.todo_list_microservice.controller;

import co.edu.emarrugo.todo_list_microservice.handler.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class TodoListController {
    private final Handler handler;

    @Bean
    public RouterFunction<ServerResponse> todoListRoutes() {
        return route(GET("/api/todolists"), handler::getAllTodoLists)
                .andRoute(GET("/api/todolists/{id}"), handler::getTodoListById)
                .andRoute(POST("/api/todolists"), handler::createTodoList)
                .andRoute(PUT("/api/todolists/{id}"), handler::updateTodoList)
                .andRoute(DELETE("/api/todolists/{id}"), handler::deleteTodoList)
                .andRoute(GET("/api/todolists/{id}/tasks"), handler::getTasksByTodoListId)
                .andRoute(GET("/api/todolists/{id}/withTasks"), handler::getTodoListWithTasks)
                .andRoute(GET("/api/tasks/{id}"), handler::getTaskById)
                .andRoute(POST("/api/tasks"), handler::createTask)
                .andRoute(PUT("/api/tasks/{id}"), handler::updateTask)
                .andRoute(DELETE("/api/tasks/{id}"), handler::deleteTask);
    }
}