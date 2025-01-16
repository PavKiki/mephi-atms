package ru.dsis.atms.testmanagement.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.dsis.atms.jdbc.util.nullIfZero
import ru.dsis.atms.testmanagement.dao.TaskDao
import ru.dsis.atms.testmanagement.dto.TaskDto
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class TasksRepository(val jdbcTemplate: JdbcTemplate) {
    fun findAll(): List<TaskDao> {
        val sql = """
            SELECT * FROM tasks
        """.trimIndent()
        return jdbcTemplate.query(sql, TaskDaoRowMapper())
    }

    fun save(task: TaskDto): TaskDao {
        val sql = """
            INSERT INTO tasks (summary, description, project_id, test_plan_id)
            VALUES (?, ?, ?, ?)
            RETURNING id, summary, description, project_id, test_plan_id
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, TaskDaoRowMapper(), task.summary, task.description, task.projectId, task.testPlanId)!!
    }

    internal class TaskDaoRowMapper : RowMapper<TaskDao> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): TaskDao {
            val taskDao = TaskDao()
            taskDao.id = rs.getInt("id")
            taskDao.summary = rs.getString("summary")
            taskDao.description = rs.getString("description")
            taskDao.projectId = nullIfZero(rs.getInt("project_id"))
            taskDao.testPlanId = nullIfZero(rs.getInt("test_plan_id"))
            return taskDao
        }
    }
}