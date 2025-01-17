package ru.dsis.atms.testmanagement.dao;

import ru.dsis.atms.testmanagement.dto.TestPlanDto;

public class TestPlanDao {
    private int id;
    private String name;
    private String taskKey;
    private Integer projectId;

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

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public TestPlanDto toDto() {
        var testPlanDto = new TestPlanDto();
        testPlanDto.setName(name);
        testPlanDto.setTaskKey(taskKey);
        testPlanDto.setProjectId(projectId);

        return testPlanDto;
    }
}