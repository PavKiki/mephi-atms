package ru.dsis.atms.users.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.dsis.atms.users.dao.PostgresPermission;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PermissionsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(PostgresPermission permission) {
        String sql = "INSERT INTO permissions (permission_name) VALUES (?)";
        jdbcTemplate.update(sql, permission.getPermissionName());
    }

    public PostgresPermission findByPermissionName(String permissionName) {
        String sql = "SELECT * FROM permissions WHERE permission_name = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{permissionName}, new PermissionRowMapper());
    }

    public List<PostgresPermission> findPermissionsByUserId(Long userId) {
        String sql = "SELECT p FROM permissions p " +
                     "JOIN role_permissions rp ON p.id = rp.permission_id " +
                     "JOIN roles r ON rp.role_id = r.id " +
                     "JOIN user_roles ur ON r.id = ur.role_id " +
                     "WHERE ur.user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, new PermissionRowMapper());
    }

    private static class PermissionRowMapper implements RowMapper<PostgresPermission> {
        @Override
        public PostgresPermission mapRow(ResultSet rs, int rowNum) throws SQLException {
            PostgresPermission permission = new PostgresPermission();
            permission.setId(rs.getLong("id"));
            permission.setPermissionName(rs.getString("permission_name"));
            return permission;
        }
    }
}
