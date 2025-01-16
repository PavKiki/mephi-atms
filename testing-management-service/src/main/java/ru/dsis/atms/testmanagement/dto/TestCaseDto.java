package ru.dsis.atms.testmanagement.dto;

public class TestCaseDto {
    private String name;
    private String preCondition;
    private String postCondition;
    private Integer projectId;
    private Integer testPlanId;

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
}