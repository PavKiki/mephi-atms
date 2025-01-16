package ru.dsis.atms.testmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dsis.atms.testmanagement.dao.ProjectDao;
import ru.dsis.atms.testmanagement.dao.TaskDao;
import ru.dsis.atms.testmanagement.dao.TestCaseDao;
import ru.dsis.atms.testmanagement.dao.TestPlanDao;
import ru.dsis.atms.testmanagement.dto.ProjectDto;
import ru.dsis.atms.testmanagement.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<ProjectDao> getAllProjects() {
        return projectService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDao> getProjectById(@PathVariable int id) {
        ProjectDao projectDao = projectService.findById(id);
        if (projectDao != null) {
            return new ResponseEntity<>(projectDao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ProjectDao> createProject(@RequestBody ProjectDto projectDto) {
        ProjectDao createdProjectDao = projectService.save(projectDto);
        return new ResponseEntity<>(createdProjectDao, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDao> updateProject(@PathVariable int id, @RequestBody ProjectDto projectDto) {
        ProjectDao updatedProjectDao = projectService.update(id, projectDto);
        if (updatedProjectDao != null) {
            return new ResponseEntity<>(updatedProjectDao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectDao> partialUpdateProject(@PathVariable int id, @RequestBody ProjectDto projectDto) {
        ProjectDao updatedProjectDao = projectService.partialUpdate(id, projectDto);
        if (updatedProjectDao != null) {
            return new ResponseEntity<>(updatedProjectDao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable int id) {
        boolean isDeleted = projectService.delete(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/test-plans")
    public List<TestPlanDao> getAllTestPlans(@PathVariable int id) {
        return projectService.findAllTestPlans(id);
    }

    @GetMapping("/{id}/test-cases")
    public List<TestCaseDao> getAllTestCases(@PathVariable int id) {
        return projectService.findAllTestCases(id);
    }

    @GetMapping("/{id}/task")
    public TaskDao getTask(@PathVariable int id) {
        return projectService.getTask(id);
    }
}
