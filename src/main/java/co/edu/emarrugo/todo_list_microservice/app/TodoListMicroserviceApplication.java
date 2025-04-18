package co.edu.emarrugo.todo_list_microservice.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "co.edu.emarrugo.todo_list_microservice")
public class TodoListMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoListMicroserviceApplication.class, args);
	}

}
