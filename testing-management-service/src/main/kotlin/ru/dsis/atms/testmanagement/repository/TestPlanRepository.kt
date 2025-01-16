package ru.dsis.atms.testmanagement.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.dsis.atms.jdbc.util.nullIfZero
import ru.dsis.atms.testmanagement.dao.TestPlanDao
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

    fun save(testPlanDto: TestPlanDto): TestPlanDao {
        val sql = """
            INSERT INTO test_plans (name, project_id, task_id) 
            VALUES (?, ?, ?)
            RETURNING id, name, project_id, task_id
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, TestPlanDaoRowMapper(), testPlanDto.name, testPlanDto.projectId, testPlanDto.taskId)!!
    }

    fun update(id: Int, testPlanDto: TestPlanDto): TestPlanDao? {
        val sql = """
            UPDATE test_plans
            SET name = ?, project_id = ?, task_id = ?
            WHERE id = ?
            RETURNING id, name, project_id, task_id
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, TestPlanDaoRowMapper(), testPlanDto.name, testPlanDto.projectId, testPlanDto.taskId, id)
    }

    fun delete(id: Int): Boolean {
        val sql = """
            DELETE FROM test_plans
            WHERE id = ?
        """.trimIndent()
        return jdbcTemplate.update(sql, id) > 0
    }

    private class TestPlanDaoRowMapper : RowMapper<TestPlanDao> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): TestPlanDao {
            val testPlanDao = TestPlanDao()
            testPlanDao.id = rs.getInt("id")
            testPlanDao.name = rs.getString("name")
            testPlanDao.projectId = nullIfZero(rs.getInt("project_id"))
            testPlanDao.taskId = nullIfZero(rs.getInt("task_id"))
            return testPlanDao
        }
    }
}