package ru.dsis.atms.testmanagement.dao;

import ru.dsis.atms.testmanagement.Status;
import ru.dsis.atms.testmanagement.dto.TestCaseDto;

public class TestCaseDao {
    private int id;
    private String name;
    private String preCondition;
    private String postCondition;
    private Status status;
    private Integer projectId;
    private Integer testPlanId;

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

    public String getPreCondition() {
        return preCondition;
    }

    public void setPreCondition(String preCondition) {
        this.preCondition = preCondition;
    }

    public String getPostCondition() {
        return postCondition;
    }

    public void setPostCondition(String postCondition) {
        this.postCondition = postCondition;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getTestPlanId() {
        return testPlanId;
    }

    public void setTestPlanId(Integer testPlanId) {
        this.testPlanId = testPlanId;
    }

    public TestCaseDto toDto() {
        var testCaseDto = new TestCaseDto();
        testCaseDto.setName(name);
        testCaseDto.setPreCondition(preCondition);
        testCaseDto.setPostCondition(postCondition);
        testCaseDto.setStatus(status);
        testCaseDto.setProjectId(projectId);
        testCaseDto.setTestPlanId(testPlanId);
        return testCaseDto;
    }
}