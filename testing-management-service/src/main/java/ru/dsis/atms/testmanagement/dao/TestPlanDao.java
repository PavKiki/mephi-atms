package ru.dsis.atms.testmanagement.dao;

import ru.dsis.atms.testmanagement.dto.TestPlanDto;

public class TestPlanDao {
    private int id;
    private String name;
    private Integer projectId;
    private Integer taskId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public TestPlanDto toDto() {
        var testPlanDto = new TestPlanDto();
        testPlanDto.setName(name);
        testPlanDto.setProjectId(projectId);
        testPlanDto.setTaskId(taskId);

        return testPlanDto;
    }
}