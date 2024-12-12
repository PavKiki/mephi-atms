package ru.dsis.atms.users.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.dsis.atms.users.dao.PostgresUser;

import java.util.List;

@Repository
public class UsersRepository {
    private static final String DB_NAME = "users";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<PostgresUser> findAll() {
        return jdbcTemplate.query(
            "SELECT * FROM " + DB_NAME,
            new BeanPropertyRowMapper<>(PostgresUser.class)
        );
    }

    public void addUser(PostgresUser user) {
        jdbcTemplate.update(
            "INSERT INTO " + DB_NAME + " (login, password, role_id) VALUES (?, ?, ?)",
            user.getLogin(), user.getPassword(), user.getRoleId()
        );
    }
}
