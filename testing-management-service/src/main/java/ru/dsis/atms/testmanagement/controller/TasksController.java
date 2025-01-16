package ru.dsis.atms.testmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dsis.atms.testmanagement.dao.TaskDao;
import ru.dsis.atms.testmanagement.dto.TaskDto;
import ru.dsis.atms.testmanagement.service.TasksService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private TasksService tasksService;

    @GetMapping
    public List<TaskDao> getAllTasks() {
        return tasksService.getAllTasks();
    }

    @PostMapping("/receive")
    public ResponseEntity<TaskDao> receiveTask(TaskDto task) {
        return new ResponseEntity<>(tasksService.saveTask(task), HttpStatus.CREATED);
    }
}
