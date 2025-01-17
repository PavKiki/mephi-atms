package ru.dsis.atms.testmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.client.tasks.dao.TaskDao;
import ru.dsis.atms.testmanagement.repository.PublicApiRepository;

import java.util.List;

@Service
public class PublicApiService {

    @Autowired
    private PublicApiRepository publicApiRepository;

    public List<TaskDao> getStatus(String taskKey) {
        //TODO
        return null;
    }
}
