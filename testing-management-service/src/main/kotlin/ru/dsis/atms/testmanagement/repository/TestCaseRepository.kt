package ru.dsis.atms.testmanagement.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.dsis.atms.jdbc.util.nullIfZero
import ru.dsis.atms.testmanagement.dao.TestCaseDao
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

    fun save(testCaseDto: TestCaseDto): TestCaseDao {
        val sql = """
            INSERT INTO test_cases (name, pre_condition, post_condition, test_plan_id, project_id)
            VALUES (?, ?, ?, ?, ?)
            RETURNING id, name, pre_condition, post_condition, test_plan_id, project_id
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, TestCaseDaoRowMapper(), testCaseDto.name, testCaseDto.preCondition, testCaseDto.postCondition, testCaseDto.testPlanId, testCaseDto.projectId)!!
    }

    fun update(id: Int, testCaseDto: TestCaseDto): TestCaseDao? {
        val sql = """
            UPDATE test_cases
            SET name = ?, pre_condition = ?, post_condition = ?, test_plan_id = ?, project_id = ?
            WHERE id = ?
            RETURNING id, name, pre_condition, post_condition, test_plan_id, project_id
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, TestCaseDaoRowMapper(), testCaseDto.name, testCaseDto.preCondition, testCaseDto.postCondition, testCaseDto.testPlanId, testCaseDto.projectId, id)
    }

    fun delete(id: Int): Boolean {
        val sql = """
            DELETE FROM test_cases
            WHERE id = ?
        """.trimIndent()
        return jdbcTemplate.update(sql, id) > 0
    }

    internal class TestCaseDaoRowMapper : RowMapper<TestCaseDao> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): TestCaseDao {
            val testCaseDao = TestCaseDao()
            testCaseDao.id = rs.getInt("id")
            testCaseDao.name = rs.getString("name")
            testCaseDao.preCondition = rs.getString("pre_condition")
            testCaseDao.postCondition = rs.getString("post_condition")
            testCaseDao.testPlanId = nullIfZero(rs.getInt("test_plan_id"))
            testCaseDao.projectId = nullIfZero(rs.getInt("project_id"))
            return testCaseDao
        }
    }
}