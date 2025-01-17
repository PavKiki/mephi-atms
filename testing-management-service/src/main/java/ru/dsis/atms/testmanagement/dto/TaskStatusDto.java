package ru.dsis.atms.testmanagement.dto;

import ru.dsis.atms.testmanagement.Status;
import ru.dsis.atms.testmanagement.TestType;

public class TaskStatusDto {
    private Long taskId;
    private TestType testType;
    private Status status;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public TestType getTestType() {
        return testType;
    }

    public void setTestType(TestType testType) {
        this.testType = testType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}