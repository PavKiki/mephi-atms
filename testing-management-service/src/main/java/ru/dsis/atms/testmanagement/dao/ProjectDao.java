package ru.dsis.atms.testmanagement.dao;

import ru.dsis.atms.testmanagement.dto.ProjectDto;

public class ProjectDao {
    private int id;
    private String name;
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

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public ProjectDto toDto() {
        var projectDto = new ProjectDto();
        projectDto.setName(name);
        projectDto.setTaskId(taskId);

        return projectDto;
    }
}