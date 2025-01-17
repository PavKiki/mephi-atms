package ru.dsis.atms.testmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.dsis.atms.client.tasks.TasksManagementClientConfig;
import ru.dsis.atms.jdbc.JdbcConfiguration;

@SpringBootApplication
@Import({JdbcConfiguration.class, TasksManagementClientConfig.class})
public class TestsMain {
    public static void main(String[] args) {
        SpringApplication.run(TestsMain.class, args);
    }
}