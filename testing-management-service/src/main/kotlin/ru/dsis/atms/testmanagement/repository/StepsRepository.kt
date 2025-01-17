package ru.dsis.atms.testmanagement.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.dsis.atms.jdbc.util.nullIfZero
import ru.dsis.atms.testmanagement.Status
import ru.dsis.atms.testmanagement.dao.StepDao
import ru.dsis.atms.testmanagement.dto.StepDto
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class StepsRepository(val jdbcTemplate: JdbcTemplate) {
    fun findAll(): List<StepDao> {
        val sql = """
            SELECT * FROM steps
        """.trimIndent()
        return jdbcTemplate.query(sql, StepDaoRowMapper())
    }

    fun save(stepDto: StepDto): StepDao {
        val sql = """
            INSERT INTO steps (ordering, action, expected_result, actual_result, status, test_case_id)
            VALUES (?, ?, ?, ?, ?::TASK_STATUS_ENUM, ?)
            RETURNING id, ordering, action, expected_result, actual_result, status, test_case_id
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, StepDaoRowMapper(), stepDto.ordering, stepDto.action, stepDto.expectedResult, stepDto.actualResult, stepDto.status.name, stepDto.testCaseId)!!
    }

    fun findById(id: Int): StepDao? {
        val sql = """
            SELECT * FROM steps 
            WHERE id = ?
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, StepDaoRowMapper(), id)
    }

    fun update(id: Int, stepDto: StepDto): StepDao? {
        val sql = """
            UPDATE steps
            SET ordering = ?, action = ?, expected_result = ?, actual_result = ?, status::TASK_STATUS_ENUM = ?, test_case_id = ?
            WHERE id = ?
            RETURNING id, ordering, action, expected_result, actual_result, status, test_case_id
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, StepDaoRowMapper(), stepDto.ordering, stepDto.action, stepDto.expectedResult, stepDto.actualResult, stepDto.status.name, stepDto.testCaseId, id)
    }

    fun delete(id: Int): Boolean {
        val sql = """
            DELETE FROM steps
            WHERE id = ?
        """.trimIndent()
        return jdbcTemplate.update(sql, id) > 0
    }

    internal class StepDaoRowMapper : RowMapper<StepDao> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): StepDao {
            val stepDao = StepDao()
            stepDao.id = rs.getInt("id")
            stepDao.ordering = rs.getInt("ordering")
            stepDao.action = rs.getString("action")
            stepDao.expectedResult = rs.getString("expected_result")
            stepDao.actualResult = rs.getString("actual_result")
            stepDao.status = Status.valueOf(rs.getString("status"))
            stepDao.testCaseId = nullIfZero(rs.getInt("test_case_id"))
            return stepDao
        }
    }
}