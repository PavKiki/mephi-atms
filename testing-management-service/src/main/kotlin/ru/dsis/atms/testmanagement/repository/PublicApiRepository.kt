package ru.dsis.atms.testmanagement.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.dsis.atms.testmanagement.Status
import ru.dsis.atms.testmanagement.TestType
import ru.dsis.atms.testmanagement.dto.TaskStatusDto
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class PublicApiRepository(val jdbcTemplate: JdbcTemplate) {
    fun getStatus(taskId: Long): TaskStatusDto? {
        val sql = """
            SELECT task_id, status 
            FROM test_plans
            WHERE task_id = $taskId
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, TaskStatusDtoRowMapper())
    }

    internal class TaskStatusDtoRowMapper : RowMapper<TaskStatusDto> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): TaskStatusDto {
            val taskStatusDto = TaskStatusDto()
            taskStatusDto.taskId = rs.getLong("task_id")
            taskStatusDto.status = Status.valueOf(rs.getString("status"))
            taskStatusDto.testType = TestType.`TEST PLAN`
            return taskStatusDto
        }
    }
}