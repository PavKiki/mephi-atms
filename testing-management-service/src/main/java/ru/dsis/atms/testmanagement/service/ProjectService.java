package ru.dsis.atms.testmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.testmanagement.dao.ProjectDao;
import ru.dsis.atms.testmanagement.dto.ProjectDto;
import ru.dsis.atms.testmanagement.repository.ProjectRepository;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<ProjectDao> findAll() {
        return projectRepository.findAll();
    }

    public ProjectDao findById(int id) {
        return projectRepository.findById(id);
    }

    public ProjectDao save(ProjectDto projectDto) {
        return projectRepository.save(projectDto);
    }

    public ProjectDao update(int id, ProjectDto projectDto) {
        return projectRepository.update(id, projectDto);
    }

    public ProjectDao partialUpdate(int id, ProjectDto projectDto) {
        var existingProject = projectRepository.findById(id);
        if (existingProject == null) {
            return null;
        }

        if (projectDto.getName() != null) {
            existingProject.setName(projectDto.getName());
        }
        if (projectDto.getTaskId() != null) {
            existingProject.setTaskId(projectDto.getTaskId());
        }

        return projectRepository.update(id, existingProject.toDto());
    }

    public boolean delete(int id) {
        return projectRepository.delete(id);
    }
}
