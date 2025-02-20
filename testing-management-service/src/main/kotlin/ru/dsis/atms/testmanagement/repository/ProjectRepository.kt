package ru.dsis.atms.testmanagement.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ru.dsis.atms.testmanagement.dao.ProjectDao
import ru.dsis.atms.testmanagement.dao.TestCaseDao
import ru.dsis.atms.testmanagement.dao.TestPlanDao
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
            INSERT INTO projects (name) 
            VALUES (?) 
            RETURNING id, name
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, ProjectDaoRowMapper(), projectDto.name)!!
    }

    fun update(id: Int, projectDto: ProjectDto): ProjectDao? {
        val sql = """
            UPDATE projects
            SET name = ?
            WHERE id = ?
            RETURNING id, name
        """.trimIndent()
        return jdbcTemplate.queryForObject(sql, ProjectDaoRowMapper(), projectDto.name, id)
    }

    fun delete(id: Int): Boolean {
        val sql = """
            DELETE FROM projects
            WHERE id = ?
        """.trimIndent()
        return jdbcTemplate.update(sql, id) > 0
    }

    fun findAllTestPlans(id: Int): List<TestPlanDao> {
        val sql = """
            SELECT * FROM test_plans
            WHERE project_id = ?
        """.trimIndent()
        return jdbcTemplate.query(sql, TestPlanRepository.TestPlanDaoRowMapper(), id)
    }

    fun findAllTestCases(id: Int): List<TestCaseDao> {
        val sql = """
            SELECT * FROM test_cases
            WHERE project_id = ?
        """.trimIndent()
        return jdbcTemplate.query(sql, TestCaseRepository.TestCaseDaoRowMapper(), id)
    }

    private class ProjectDaoRowMapper : RowMapper<ProjectDao> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): ProjectDao {
            val projectDao = ProjectDao()
            projectDao.id = rs.getInt("id")
            projectDao.name = rs.getString("name")
            return projectDao
        }
    }
}