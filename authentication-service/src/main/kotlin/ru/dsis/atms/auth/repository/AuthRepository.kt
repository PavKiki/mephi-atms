package ru.dsis.atms.auth.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.dsis.atms.auth.dao.UserDao
import ru.dsis.atms.auth.dao.UserDto
import java.sql.ResultSet
import java.sql.SQLException
import java.time.ZoneOffset

@Repository
class AuthRepository(val jdbcTemplate: JdbcTemplate) {
    fun findByUserCredentials(userDao: UserDao): UserDto? {
        val sql = """
            SELECT u.username, u.name, u.created_at, u.role_name
            FROM users u 
            WHERE u.username = ? AND u.password = ?
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, UserInfoRowMapper(), userDao.username, userDao.password)
    }

    fun userIdByUsername(username: String): Int? {
        val sql = """
            SELECT id FROM users WHERE username = ?
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, Int::class.java, username)
                   ?: throw IllegalArgumentException("User id was not found by $username")
    }

    fun saveToken(userDto: UserDto, token: String) {
        val userId = userIdByUsername(userDto.username)
        val sql = """
            INSERT INTO tokens (user_id, token)
            VALUES (?, ?)
        """.trimIndent()
        jdbcTemplate.update(sql, userId, token)
    }

    fun tokenExistsByUsername(username: String, token: String): Boolean {
        val userId = userIdByUsername(username)
        val sql = """
            SELECT EXISTS (
                SELECT 1 FROM tokens 
                WHERE user_id = ? AND token = ?
            )
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, Boolean::class.java, userId, token)!!
    }

    private class UserInfoRowMapper : RowMapper<UserDto> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): UserDto {
            val user = UserDto()
            user.username = rs.getString("username")
            user.name = rs.getString("name")
            user.role = rs.getString("role_name")
            user.created = rs.getTimestamp("created_at").toInstant().atZone(ZoneOffset.UTC)

            return user
        }
    }
}