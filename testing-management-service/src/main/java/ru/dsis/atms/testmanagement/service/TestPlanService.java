package ru.dsis.atms.testmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsis.atms.testmanagement.dao.TestCaseDao;
import ru.dsis.atms.testmanagement.dao.TestPlanDao;
import ru.dsis.atms.testmanagement.dto.TestPlanCreationDto;
import ru.dsis.atms.testmanagement.dto.TestPlanDto;
import ru.dsis.atms.testmanagement.repository.TestPlanRepository;

import java.util.List;

@Service
public class TestPlanService {

    @Autowired
    private TestPlanRepository testPlanRepository;

    public List<TestPlanDao> findAll() {
        return testPlanRepository.findAll();
    }

    public TestPlanDao findById(int id) {
        return testPlanRepository.findById(id);
    }

    public TestPlanDao save(TestPlanCreationDto testPlanCreationDto) {
        return testPlanRepository.save(testPlanCreationDto);
    }

    public TestPlanDao update(int id, TestPlanDto testPlanDto) {
        return testPlanRepository.update(id, testPlanDto);
    }

    public TestPlanDao partialUpdate(int id, TestPlanDto testPlanDto) {
        var existingTestPlan = testPlanRepository.findById(id);
        if (existingTestPlan == null) {
            return null;
        }

        if (testPlanDto.getName() != null) {
            existingTestPlan.setName(testPlanDto.getName());
        }
        if (testPlanDto.getStatus() != null) {
            existingTestPlan.setStatus(testPlanDto.getStatus());
        }
        if (testPlanDto.getProjectId() != null) {
            existingTestPlan.setProjectId(testPlanDto.getProjectId());
        }

        return update(id, existingTestPlan.toDto());
    }

    public boolean delete(int id) {
        return testPlanRepository.delete(id);
    }

    public List<TestCaseDao> getTestCases(int id) {
        return testPlanRepository.getTestCases(id);
    }
}
