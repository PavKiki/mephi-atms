package ru.dsis.atms.testmanagement.dao;

import ru.dsis.atms.testmanagement.Status;
import ru.dsis.atms.testmanagement.dto.TestPlanDto;

public class TestPlanDao {
    private int id;
    private String name;
    private Integer taskId;
    private Status status;
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

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public TestPlanDto toDto() {
        var testPlanDto = new TestPlanDto();
        testPlanDto.setName(name);
        testPlanDto.setTaskId(taskId);
        testPlanDto.setStatus(status);
        testPlanDto.setProjectId(projectId);

        return testPlanDto;
    }
}