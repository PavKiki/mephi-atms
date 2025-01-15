package ru.dsis.atms.users.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.dsis.atms.users.dao.data.UserData
import ru.dsis.atms.users.dao.data.UserRegistrationData
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class UsersRepository(val jdbcTemplate: JdbcTemplate) {
    fun save(user: UserRegistrationData) {
        val sql = """
            INSERT INTO users (username, password, name, role_id) 
            VALUES (?, ?, ?, ?)
        """.trimIndent()
        jdbcTemplate.update(sql, user.username, user.password, user.name, user.roleId)
    }

    fun findAll(): List<UserData> {
        val sql = """
            SELECT u.id, u.username, u.name, u.created_at, r.role_name
            FROM users u 
            INNER JOIN roles r ON u.role_id = r.id
            ORDER BY u.id ASC
        """.trimIndent()
        return jdbcTemplate.query(sql, UserRowMapper())
    }

    fun findById(id: Long): UserData? {
        val sql = """
            SELECT u.id, u.username, u.name, u.created_at, r.role_name
            FROM users u
            INNER JOIN roles r 
            ON u.role_id = r.id
            WHERE u.id = ?
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, UserRowMapper(), id)
    }

    fun findByUsername(username: String): UserData? {
        val sql = """
            SELECT u.id, u.username, u.name, u.created_at, r.role_name
            FROM users u
            INNER JOIN roles r 
            ON u.role_id = r.id
            WHERE u.username = ?
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, UserRowMapper(), username)
    }

    fun patchUsername(userId: Long, username: String) {
        val sql = """
            UPDATE users
            SET username = ?
            WHERE id = ?
        """.trimIndent()
        jdbcTemplate.update(sql, username, userId)
    }

    fun patchPassword(userId: Long, password: String) {
        val sql = """
            UPDATE users
            SET password = ?
            WHERE id = ?
        """.trimIndent()
        jdbcTemplate.update(sql, password, userId)
    }

    fun patchName(userId: Long, name: String) {
        val sql = """
            UPDATE users
            SET name = ?
            WHERE id = ?
        """.trimIndent()
        jdbcTemplate.update(sql, name, userId)
    }

    fun patchRole(userId: Long, roleId: Long) {
        val sql = """
            UPDATE users
            SET role_id = ?
            WHERE id = ?
        """.trimIndent()
        jdbcTemplate.update(sql, roleId, userId)
    }

    fun delete(id: Long) {
        val sql = """
            DELETE FROM users
            WHERE id = ?
        """.trimIndent()
        jdbcTemplate.update(sql, id)
    }

    private class UserRowMapper : RowMapper<UserData> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): UserData {
            val user = UserData()
            user.id = rs.getLong("id")
            user.username = rs.getString("username")
            user.name = rs.getString("name")
            user.role = rs.getString("role_name")
            user.created = rs.getTimestamp("created_at").toLocalDateTime()

            return user
        }
    }
}