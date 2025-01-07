package ru.dsis.atms.users.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.dsis.atms.users.dao.postgres.PostgresRole
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class RolesRepository(val jdbcTemplate: JdbcTemplate) {
    fun findAll(): List<PostgresRole> {
        val sql = """
            SELECT r.id as role_id, r.role_name FROM roles r
        """.trimIndent()
        return jdbcTemplate.query(sql, RoleRowMapper())
    }

    fun findByRoleId(roleId: Long): PostgresRole? {
        val sql = """
            SELECT r.id as role_id, r.role_name FROM roles r
            WHERE r.id = ?
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, RoleRowMapper(), roleId)
    }

    fun findByRoleName(roleName: String): PostgresRole? {
        val sql = """
            SELECT r.id as role_id, r.role_name FROM roles r
            WHERE role_name = ?
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, RoleRowMapper(), roleName)
    }

    fun findByUserId(userId: Long): PostgresRole? {
        val sql = """
            SELECT r.id as role_id, r.role_name FROM roles r
            INNER JOIN users u ON r.id = u.role_id
            WHERE u.id = ?
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, RoleRowMapper(), userId)
    }

    fun findByUsername(username: String): PostgresRole? {
        val sql = """
            SELECT r.id as role_id, r.role_name FROM roles r
            INNER JOIN users u ON r.id = u.role_id
            WHERE u.username = ?
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, RoleRowMapper(), username)
    }

    private class RoleRowMapper : RowMapper<PostgresRole> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): PostgresRole {
            val role = PostgresRole()
            role.id = rs.getLong("role_id")
            role.roleName = rs.getString("role_name")
            return role
        }
    }
}