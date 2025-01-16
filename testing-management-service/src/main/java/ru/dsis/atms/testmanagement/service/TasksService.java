package ru.dsis.atms.testmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.testmanagement.dao.TaskDao;
import ru.dsis.atms.testmanagement.dto.TaskDto;
import ru.dsis.atms.testmanagement.repository.TasksRepository;

import java.util.List;

@Service
public class TasksService {

    @Autowired
    TasksRepository tasksRepository;

    public List<TaskDao> getAllTasks() {
        return tasksRepository.findAll();
    }

    public TaskDao saveTask(TaskDto task) {
        return tasksRepository.save(task);
    }
}
