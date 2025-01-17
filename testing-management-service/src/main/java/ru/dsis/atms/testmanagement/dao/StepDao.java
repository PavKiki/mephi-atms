package ru.dsis.atms.testmanagement.dao;

import ru.dsis.atms.testmanagement.dto.StepDto;

public class StepDao {
    private int id;
    private int ordering;
    private String action;
    private String expectedResult;
    private String actualResult;
    private boolean status;
    private Integer testCaseId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    public String getActualResult() {
        return actualResult;
    }

    public void setActualResult(String actualResult) {
        this.actualResult = actualResult;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(Integer testCaseId) {
        this.testCaseId = testCaseId;
    }

    public StepDto toDto() {
        var stepDto = new StepDto();
        stepDto.setOrdering(ordering);
        stepDto.setAction(action);
        stepDto.setExpectedResult(expectedResult);
        stepDto.setActualResult(actualResult);
        stepDto.setStatus(status);
        stepDto.setTestCaseId(testCaseId);
        return stepDto;
    }
}
