package ru.dsis.atms.testmanagement.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.dsis.atms.jdbc.util.nullIfZero
import ru.dsis.atms.testmanagement.Status
import ru.dsis.atms.testmanagement.dao.TestCaseDao
import ru.dsis.atms.testmanagement.dao.TestPlanDao
import ru.dsis.atms.testmanagement.dto.TestPlanCreationDto
import ru.dsis.atms.testmanagement.dto.TestPlanDto
import java.sql.ResultSet
import java.sql.SQLException

@Repository
class TestPlanRepository(val jdbcTemplate: JdbcTemplate) {
    fun findAll(): List<TestPlanDao> {
        val sql = """
            SELECT * FROM test_plans
        """.trimIndent()
        return jdbcTemplate.query(sql, TestPlanDaoRowMapper())
    }

    fun findById(id: Int): TestPlanDao? {
        val sql = """
            SELECT * FROM test_plans 
            WHERE id = ?
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, TestPlanDaoRowMapper(), id)
    }

    fun save(testPlanCreationDto: TestPlanCreationDto): TestPlanDao {
        val sql = """
            INSERT INTO test_plans (name, project_id, task_id) 
            VALUES (?, ?, ?)
            RETURNING id, name, project_id, status, task_id
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, TestPlanDaoRowMapper(), testPlanCreationDto.name, testPlanCreationDto.projectId, testPlanCreationDto.taskId)!!
    }

    fun update(id: Int, testPlanDto: TestPlanDto): TestPlanDao? {
        val sql = """
            UPDATE test_plans
            SET name = ?, project_id = ?, status = ?::TASK_STATUS_ENUM, task_id = ?
            WHERE id = ?
            RETURNING id, name, project_id, status, task_id
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, TestPlanDaoRowMapper(), testPlanDto.name, testPlanDto.projectId, testPlanDto.status.name, testPlanDto.taskId, id)
    }

    fun delete(id: Int): Boolean {
        val sql = """
            DELETE FROM test_plans
            WHERE id = ?
        """.trimIndent()
        return jdbcTemplate.update(sql, id) > 0
    }

    fun getTestCases(id: Int): List<TestCaseDao> {
        val sql = """
            SELECT * FROM test_cases
            WHERE test_plan_id = ?
        """.trimIndent()
        return jdbcTemplate.query(sql, TestCaseRepository.TestCaseDaoRowMapper(), id)
    }

    internal class TestPlanDaoRowMapper : RowMapper<TestPlanDao> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): TestPlanDao {
            val testPlanDao = TestPlanDao()
            testPlanDao.id = rs.getInt("id")
            testPlanDao.name = rs.getString("name")
            testPlanDao.taskId = rs.getInt("task_id")
            testPlanDao.status = Status.valueOf(rs.getString("status"))
            testPlanDao.projectId = nullIfZero(rs.getInt("project_id"))
            return testPlanDao
        }
    }
}