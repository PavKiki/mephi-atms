package ru.dsis.atms.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Import;
//import ru.dsis.atms.jdbc.JdbcConfiguration;

@SpringBootApplication
//@Import(JdbcConfiguration.class)
public class UsersMain {
    public static void main(String[] args) {
        SpringApplication.run(UsersMain.class, args);
    }
}