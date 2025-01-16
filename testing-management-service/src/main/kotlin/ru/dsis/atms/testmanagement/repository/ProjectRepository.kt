package ru.dsis.atms.testmanagement.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.dsis.atms.jdbc.util.nullIfZero
import ru.dsis.atms.testmanagement.dao.ProjectDao
import ru.dsis.atms.testmanagement.dto.ProjectDto
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class ProjectRepository(val jdbcTemplate: JdbcTemplate) {
    fun findAll(): MutableList<ProjectDao> {
        val sql = """
            SELECT * FROM projects
        """.trimIndent()
        return jdbcTemplate.query(sql, ProjectDaoRowMapper())
    }

    fun findById(id: Int): ProjectDao? {
        val sql = """
            SELECT * FROM projects 
            WHERE id = ?
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, ProjectDaoRowMapper(), id)
    }

    fun save(projectDto: ProjectDto): ProjectDao {
        val sql = """
            INSERT INTO projects (name, task_id) 
            VALUES (?, ?) 
            RETURNING id, name, task_id
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, ProjectDaoRowMapper(), projectDto.name, projectDto.taskId)!!
    }

    fun update(id: Int, projectDto: ProjectDto): ProjectDao? {
        val sql = """
            UPDATE projects
            SET name = ?, task_id = ?
            WHERE id = ?
            RETURNING id, name, task_id
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, ProjectDaoRowMapper(), projectDto.name, projectDto.taskId, id)
    }

    fun delete(id: Int): Boolean {
        val sql = """
            DELETE FROM projects
            WHERE id = ?
        """.trimIndent()
        return jdbcTemplate.update(sql, id) > 0
    }

    private class ProjectDaoRowMapper : RowMapper<ProjectDao> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): ProjectDao {
            val projectDao = ProjectDao()
            projectDao.id = rs.getInt("id")
            projectDao.name = rs.getString("name")
            projectDao.taskId = nullIfZero(rs.getInt("task_id"))
            return projectDao
        }
    }
}