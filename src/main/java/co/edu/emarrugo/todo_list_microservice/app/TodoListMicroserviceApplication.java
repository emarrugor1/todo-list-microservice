package co.edu.emarrugo.todo_list_microservice.app;

import co.edu.emarrugo.todo_list_microservice.model.config.PostgresqlConnectionProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "co.edu.emarrugo.todo_list_microservice")
@EnableR2dbcRepositories(basePackages = "co.edu.emarrugo.todo_list_microservice.model.repository")
@EnableConfigurationProperties(PostgresqlConnectionProperties.class)
public class TodoListMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoListMicroserviceApplication.class, args);
	}

}
