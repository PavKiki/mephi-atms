package ru.dsis.atms.auth.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.dsis.atms.auth.dao.UserDao
import ru.dsis.atms.auth.dao.UserInfo
import java.sql.ResultSet
import java.sql.SQLException
import java.time.ZoneOffset

@Repository
class AuthRepository(val jdbcTemplate: JdbcTemplate) {
    fun findByUserCredentials(userDao: UserDao): UserInfo? {
        val sql = """
            SELECT u.username, u.name, u.created_at, u.role_name
            FROM users u 
            WHERE u.username = ? AND u.password = ?
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, UserInfoRowMapper(), userDao.username, userDao.password)
    }

    private class UserInfoRowMapper : RowMapper<UserInfo> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): UserInfo {
            val user = UserInfo()
            user.username = rs.getString("username")
            user.name = rs.getString("name")
            user.role = rs.getString("role_name")
            user.created = rs.getTimestamp("created_at").toInstant().atZone(ZoneOffset.UTC)

            return user
        }
    }
}