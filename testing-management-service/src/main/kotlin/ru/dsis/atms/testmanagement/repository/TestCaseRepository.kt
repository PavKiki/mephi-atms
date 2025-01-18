package ru.dsis.atms.testmanagement.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.dsis.atms.jdbc.util.nullIfZero
import ru.dsis.atms.testmanagement.Status
import ru.dsis.atms.testmanagement.dao.StepDao
import ru.dsis.atms.testmanagement.dao.TestCaseDao
import ru.dsis.atms.testmanagement.dto.TestCaseCreationDto
import ru.dsis.atms.testmanagement.dto.TestCaseDto
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class TestCaseRepository(val jdbcTemplate: JdbcTemplate) {
    fun findAll(): List<TestCaseDao> {
        val sql = """
            SELECT * FROM test_cases
        """.trimIndent()
        return jdbcTemplate.query(sql, TestCaseDaoRowMapper())
    }

    fun findById(id: Int): TestCaseDao? {
        val sql = """
            SELECT * FROM test_cases 
            WHERE id = ?
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, TestCaseDaoRowMapper(), id)
    }

    fun save(testCaseCreationDto: TestCaseCreationDto): TestCaseDao {
        val sql = """
            INSERT INTO test_cases (name, pre_condition, post_condition, test_plan_id)
            VALUES (?, ?, ?, ?)
            RETURNING id, name, pre_condition, post_condition, status, test_plan_id, project_id
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, TestCaseDaoRowMapper(), testCaseCreationDto.name, testCaseCreationDto.preCondition, testCaseCreationDto.postCondition, testCaseCreationDto.testPlanId)!!
    }

    fun update(id: Int, testCaseDto: TestCaseDto): TestCaseDao? {
        val sql = """
            UPDATE test_cases
            SET name = ?, pre_condition = ?, post_condition = ?, status = ?::TASK_STATUS_ENUM, test_plan_id = ?, project_id = ?
            WHERE id = ?
            RETURNING id, name, pre_condition, post_condition, status, test_plan_id, project_id
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, TestCaseDaoRowMapper(), testCaseDto.name, testCaseDto.preCondition, testCaseDto.postCondition, testCaseDto.status.name, testCaseDto.testPlanId, testCaseDto.projectId, id)
    }

    fun delete(id: Int): Boolean {
        val sql = """
            DELETE FROM test_cases
            WHERE id = ?
        """.trimIndent()
        return jdbcTemplate.update(sql, id) > 0
    }

    fun getSteps(id: Int): List<StepDao> {
        val sql = """
            SELECT * FROM steps
            WHERE test_case_id = ?
        """.trimIndent()
        return jdbcTemplate.query(sql, StepsRepository.StepDaoRowMapper(), id)
    }

    internal class TestCaseDaoRowMapper : RowMapper<TestCaseDao> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): TestCaseDao {
            val testCaseDao = TestCaseDao()
            testCaseDao.id = rs.getInt("id")
            testCaseDao.name = rs.getString("name")
            testCaseDao.preCondition = rs.getString("pre_condition")
            testCaseDao.postCondition = rs.getString("post_condition")
            testCaseDao.status = Status.valueOf(rs.getString("status"))
            testCaseDao.testPlanId = nullIfZero(rs.getInt("test_plan_id"))
            testCaseDao.projectId = nullIfZero(rs.getInt("project_id"))
            return testCaseDao
        }
    }
}