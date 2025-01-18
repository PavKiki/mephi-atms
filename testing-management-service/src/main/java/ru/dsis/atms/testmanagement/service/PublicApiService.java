package ru.dsis.atms.testmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.testmanagement.dto.TaskStatusDto;
import ru.dsis.atms.testmanagement.repository.PublicApiRepository;

@Service
public class PublicApiService {

    @Autowired
    private PublicApiRepository publicApiRepository;

    public TaskStatusDto getStatus(Long taskId) {
        return publicApiRepository.getStatus(taskId);
    }
}
