package ru.dsis.atms.users.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.dsis.atms.users.dao.data.UserData;
import ru.dsis.atms.users.dao.postgres.PostgresRole;
import ru.dsis.atms.users.dao.postgres.PostgresUser;
import ru.dsis.atms.users.dao.data.UserDataWithPassword;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UsersRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(UserDataWithPassword user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    }
//
//    public void deleteById(Long id) {
//        String sql = "DELETE FROM users WHERE id = ?";
//        jdbcTemplate.update(sql, id);
//    }

    public List<UserData> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public PostgresUser findByUsername(String username) {
        String sql = "SELECT u.*, r.* " +
                     "FROM users u " +
                     "INNER JOIN roles r ON u.role_id = r.id " +
                     "WHERE u.username = ?";
        return jdbcTemplate.queryForObject(sql, new UserWithRoleRowMapper(), username);
    }

    public void addRoleToUser(PostgresUser user, PostgresRole role) {
        String sql = "UPDATE users " +
                     "SET role_id = ? " +
                     "WHERE username = ?";
        jdbcTemplate.update(sql, role.getId(), user.getUsername());
    }

    private static class UserRowMapper implements RowMapper<UserData> {
        @Override
        public UserData mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserData user = new UserData();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));

            return user;
        }
    }

    private static class UserWithRoleRowMapper implements RowMapper<PostgresUser> {
        @Override
        public PostgresUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            PostgresUser user = new PostgresUser();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));

            PostgresRole role = new PostgresRole();
            role.setId(rs.getLong("role_id"));
            role.setRoleName(rs.getString("role_name"));

            user.setRole(role);

            return user;
        }
    }
}

