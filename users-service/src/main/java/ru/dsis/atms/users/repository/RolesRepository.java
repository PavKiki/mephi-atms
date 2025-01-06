package ru.dsis.atms.users.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.dsis.atms.users.dao.data.RoleData;
import ru.dsis.atms.users.dao.postgres.PostgresRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RolesRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(RoleData role) {
        String sql = "INSERT INTO roles (role_name) VALUES (?)";
        jdbcTemplate.update(sql, role.getRoleName());
    }

    public List<PostgresRole> findAll() {
        String sql = "SELECT * FROM roles";
        return jdbcTemplate.query(sql, new RoleRowMapper());
    }
//
//    public List<PostgresRole> findRoleByUsername(String username) {
//        String sql = "SELECT r FROM roles r " +
//                     "JOIN user_roles ur ON r.id = ur.role_id " +
//                     "JOIN users u ON u.id = ur.user_id " +
//                     "WHERE u.username = ?";
//        return jdbcTemplate.query(sql, new RoleRowMapper(), username);
//    }

    public PostgresRole findByRoleName(String roleName) {
        String sql = "SELECT * FROM roles WHERE role_name = ?";
        return jdbcTemplate.queryForObject(sql, new RoleRowMapper(), roleName);
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