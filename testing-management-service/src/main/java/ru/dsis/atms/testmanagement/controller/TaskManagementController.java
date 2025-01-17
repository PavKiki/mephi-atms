package ru.dsis.atms.testmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dsis.atms.client.tasks.TasksManagementClient;
import ru.dsis.atms.client.tasks.dao.TaskDao;
import ru.dsis.atms.client.tasks.dao.TaskManagementProjectDao;

import java.util.List;

@RestController
@RequestMapping("/task-management")
public class TaskManagementController {

    @Value("${atms.web.task.management.fetch.projects.endpoint}")
    private String fetchProjectsEndpoint;

    @Value("${atms.web.task.management.fetch.tasks.endpoint}")
    private String fetchTasksEndpoint;

    @Autowired
    private TasksManagementClient tasksManagementClient;

    @GetMapping("/projects")
    public List<TaskManagementProjectDao> fetchProjects() {
        return tasksManagementClient.fetchProjects(fetchProjectsEndpoint);
    }

    @GetMapping("/projects/{id}/tasks")
    public List<TaskDao> getAllTasks(@PathVariable int id) {
        return tasksManagementClient.fetchTasks(fetchTasksEndpoint, id);
    }
}
