package ru.dsis.atms.users.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.dsis.atms.users.dao.PostgresRole;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RolesRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(PostgresRole role) {
        String sql = "INSERT INTO roles (role_name) VALUES (?)";
        jdbcTemplate.update(sql, role.getRoleName());
    }

    public PostgresRole findByRoleName(String roleName) {
        String sql = "SELECT * FROM roles WHERE role_name = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{roleName}, new RoleRowMapper());
    }

    private static class RoleRowMapper implements RowMapper<PostgresRole> {
        @Override
        public PostgresRole mapRow(ResultSet rs, int rowNum) throws SQLException {
            PostgresRole role = new PostgresRole();
            role.setId(rs.getLong("id"));
            role.setRoleName(rs.getString("role_name"));
            return role;
        }
    }
}