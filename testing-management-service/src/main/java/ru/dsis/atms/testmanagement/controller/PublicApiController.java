package ru.dsis.atms.testmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dsis.atms.client.tasks.dao.TaskDao;
import ru.dsis.atms.testmanagement.dao.ProjectDao;
import ru.dsis.atms.testmanagement.service.ProjectService;
import ru.dsis.atms.testmanagement.service.PublicApiService;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicApiController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private PublicApiService publicApiService;

    @GetMapping("/projects")
    public List<ProjectDao> getAllProjects() {
        return projectService.findAll();
    }

    @GetMapping("/tasks/{taskKey}/status")
    public List<TaskDao> getAllTasks(@PathVariable String taskKey) {
        return publicApiService.getStatus(taskKey);
    }
}
