package ru.dsis.atms.users.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.dsis.atms.users.dao.postgres.PostgresPermission
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class PermissionsRepository(val jdbcTemplate: JdbcTemplate) {
    fun findAll(): List<PostgresPermission> {
        val sql = """
            SELECT p.id as permission_id, p.permission_name FROM permissions p
        """.trimIndent()
        return jdbcTemplate.query(sql, PermissionRowMapper())
    }

    fun findById(permissionId: Long): PostgresPermission? {
        val sql = """
            SELECT p.id as permission_id, p.permission_name FROM permissions p
            WHERE p.id = ?
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, PermissionRowMapper(), permissionId)
    }

    fun findByName(permissionName: String): PostgresPermission? {
        val sql = """
            SELECT p.id as permission_id, p.permission_name FROM permissions p
            WHERE p.permission_name = ?
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, PermissionRowMapper(), permissionName)
    }

    fun findByRoleId(roleId: Long): List<PostgresPermission> {
        val sql = """
            SELECT p.id as permission_id, p.permission_name FROM permissions p
            INNER JOIN role_permissions rp ON p.id = rp.permission_id
            INNER JOIN roles r ON rp.role_id = r.id
            WHERE r.id = ?
        """.trimIndent()
        return jdbcTemplate.query(sql, PermissionRowMapper(), roleId)
    }

    fun findByRoleName(roleName: String): List<PostgresPermission> {
        val sql = """
            SELECT p.id as permission_id, p.permission_name FROM permissions p
            INNER JOIN role_permissions rp ON p.id = rp.permission_id
            INNER JOIN roles r ON rp.role_id = r.id
            WHERE r.role_name = ?
        """.trimIndent()
        return jdbcTemplate.query(sql, PermissionRowMapper(), roleName)
    }

    fun findPermissionsByUserId(userId: Long): List<PostgresPermission> {
        val sql = """
            SELECT p.id as permission_id, p.permission_name FROM permissions p
            INNER JOIN role_permissions rp ON p.id = rp.permission_id
            INNER JOIN roles r ON rp.role_id = r.id
            INNER JOIN users u ON u.role_id = r.id
            WHERE u.id = ?
        """.trimIndent()
        return jdbcTemplate.query(sql, PermissionRowMapper(), userId)
    }

    fun findPermissionsByUsername(username: String): List<PostgresPermission> {
        val sql = """
            SELECT p.id as permission_id, p.permission_name FROM permissions p
            INNER JOIN role_permissions rp ON p.id = rp.permission_id
            INNER JOIN roles r ON rp.role_id = r.id
            INNER JOIN users u ON u.role_id = r.id
            WHERE u.username = ?
        """.trimIndent()
        return jdbcTemplate.query(sql, PermissionRowMapper(), username)
    }

    private class PermissionRowMapper : RowMapper<PostgresPermission> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): PostgresPermission {
            val permission = PostgresPermission()
            permission.id = rs.getLong("permission_id")
            permission.permissionName = rs.getString("permission_name")
            return permission
        }
    }
}