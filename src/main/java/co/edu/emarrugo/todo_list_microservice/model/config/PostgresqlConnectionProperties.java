package co.edu.emarrugo.todo_list_microservice.model.config;

// TODO: Load properties from the application.yaml file or from secrets manager
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.r2dbc")
public record PostgresqlConnectionProperties(
        String host,
        Integer port,
        String database,
        String schema,
        String username,
        String password) {
}
